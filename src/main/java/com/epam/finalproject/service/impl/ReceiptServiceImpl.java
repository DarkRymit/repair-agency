package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptDeliveryCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptItemCreateRequest;
import com.epam.finalproject.payload.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptDeliveryUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptItemUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.repository.*;
import com.epam.finalproject.service.ReceiptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.money.Monetary.getCurrency;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    ReceiptRepository receiptRepository;

    ReceiptStatusRepository receiptStatusRepository;

    ReceiptStatusFlowRepository receiptStatusFlowRepository;

    RepairCategoryRepository repairCategoryRepository;

    RepairWorkRepository repairWorkRepository;

    AppCurrencyRepository appCurrencyRepository;

    WalletRepository walletRepository;
    UserRepository userRepository;

    ModelMapper modelMapper;

    @Override
    public List<ReceiptDTO> findAll() {
        return receiptRepository.findAll(Sort.by("creationTime"))
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReceiptDTO> findAll(Pageable pageable) {
        return receiptRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    @Transactional
    @Loggable
    public ReceiptDTO createNew(ReceiptCreateRequest createRequest, String username) {
        Receipt receipt = new Receipt();

        AppCurrency currency = appCurrencyRepository.findByCode(createRequest.getPriceCurrency()).orElseThrow();

        User customer = userRepository.findByUsername(username).orElseThrow();

        RepairCategory category = repairCategoryRepository.findById(createRequest.getCategoryId()).orElseThrow();

        ReceiptDelivery delivery = buildBy(createRequest.getReceiptDelivery(), receipt);

        Set<ReceiptItem> receiptItems = getReceiptItemSet(createRequest, receipt);


        receipt.setUser(customer);

        receipt.setStatus(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow());

        receipt.setCategory(category);

        receipt.setPriceCurrency(currency);

        receipt.setItems(receiptItems);

        receipt.setDelivery(delivery);

        receipt.setNote(createRequest.getNote());

        receiptRepository.save(receipt);

        return constructDTO(receipt);
    }

    @Override
    @Transactional
    @Loggable
    public ReceiptDTO update(ReceiptUpdateRequest request) {
        Receipt receipt = receiptRepository.findById(request.getId()).orElseThrow();

        AppCurrency currency = appCurrencyRepository.findByCode(request.getPriceCurrency()).orElseThrow();

        Set<ReceiptItem> receiptItems = getReceiptItemSet(request, receipt);

        receipt.setMaster(userRepository.findByUsername(request.getMasterUsername()).orElseThrow());

        receipt.setStatus(receiptStatusRepository.findByName(request.getReceiptStatus()).orElseThrow());

        receipt.getItems().clear();
        receipt.getItems().addAll(receiptItems);

        receipt.setTotalPrice(getReceiptTotalPriceAmount(receiptItems));

        receipt.setPriceCurrency(currency);

        mergeDeliveryInto(receipt, buildBy(request.getReceiptDelivery(), receipt));

        receipt.setNote(request.getNote());

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return constructDTO(resultReceipt);
    }

    @Override
    public ReceiptDTO updateStatus(Long receiptId, Long statusId, String username) {
        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow();
        ReceiptStatus newStatus = receiptStatusRepository.findById(statusId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();
        if (!receiptStatusFlowRepository.existsByFromStatusAndToStatusAndRoleIn(receipt.getStatus(), newStatus, user.getRoles())) {
            throw new NoSuchElementException("not supported");
        }
        receipt.setStatus(newStatus);
        Receipt result = receiptRepository.save(receipt);
        return constructDTO(result);
    }

    @Override
    @Loggable
    public ReceiptDTO constructDTO(Receipt receipt) {
        return modelMapper.map(receipt, ReceiptDTO.class);
    }

    @Override
    public ReceiptDTO findById(Long id) {
        return constructDTO(receiptRepository.findById(id).orElseThrow());
    }

    @Override
    public ReceiptDTO pay(ReceiptPayRequest payRequest, String username) {

        Receipt receipt = receiptRepository.findById(payRequest.getId()).orElseThrow();
        if(!receipt.getStatus().getName().equals(ReceiptStatusEnum.WAIT_FOR_PAYMENT)){
            throw new IllegalStateException("Receipt not wait for payment");
        }
        Wallet wallet = walletRepository.findById(payRequest.getWalletId()).orElseThrow();
        if (!wallet.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("Wallet username and username not match");
        }
        if (!receipt.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("Receipt username and username not match");
        }
        Money receiptMoney = Money.of(receipt.getTotalPrice(), receipt.getPriceCurrency().getCode());
        Money walletMoney = Money.of(wallet.getMoneyAmount(), wallet.getMoneyCurrency().getCode());


        if (!receiptMoney.getCurrency().equals(walletMoney.getCurrency())){
            throw new IllegalArgumentException("Not Match Currency");
        }
        Money walletRemainder = walletMoney.subtract(receiptMoney);
        if(walletRemainder.isNegative()){
            throw new IllegalStateException("Negative money on wallet remain");
        }
        wallet.setMoneyAmount(walletRemainder.getNumberStripped());
        walletRepository.save(wallet);

        ReceiptStatus paid = receiptStatusRepository.findByName(ReceiptStatusEnum.PAID).orElseThrow();
        receipt.setStatus(paid);
        receipt = receiptRepository.save(receipt);

        return constructDTO(receipt);

    }

    private void mergeDeliveryInto(Receipt receipt, ReceiptDelivery delivery) {
        ReceiptDelivery oldDelivery = receipt.getDelivery();
        if (!oldDelivery.equals(delivery)) {
            oldDelivery.setCity(delivery.getCity());
            oldDelivery.setCountry(delivery.getCountry());
            oldDelivery.setState(delivery.getState());
            oldDelivery.setLocalAddress(delivery.getLocalAddress());
            oldDelivery.setPostalCode(delivery.getPostalCode());
        }
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptUpdateRequest updateRequest, Receipt receipt) {
        return updateRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt))
                .collect(Collectors.toSet());
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptCreateRequest createRequest, Receipt receipt) {
        return createRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt))
                .collect(Collectors.toSet());
    }

    private ReceiptDelivery buildBy(ReceiptDeliveryCreateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .receipt(receipt)
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptDelivery buildBy(ReceiptDeliveryUpdateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .receipt(receipt)
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptItem buildBy(ReceiptItemCreateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder().receipt(receipt).repairWork(repairWork).build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .build();
    }

    private BigDecimal getReceiptTotalPriceAmount(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
