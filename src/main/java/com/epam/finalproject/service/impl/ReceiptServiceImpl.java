package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptDeliveryCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptItemCreateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptDeliveryUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptItemUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.repository.*;
import com.epam.finalproject.service.ReceiptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    ReceiptRepository receiptRepository;

    ReceiptStatusRepository receiptStatusRepository;

    RepairCategoryRepository repairCategoryRepository;

    RepairWorkRepository repairWorkRepository;

    AppCurrencyRepository appCurrencyRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;

    @Override
    public List<Receipt> findAll() {
        return receiptRepository.findAll(Sort.by("creationTime"));
    }

    @Override
    public Page<Receipt> findAll(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @Loggable
    public Receipt createNew(ReceiptCreateRequest createRequest) {
        Receipt receipt = new Receipt();

        AppCurrency currency = appCurrencyRepository.findByCode(createRequest.getPriceCurrency()).orElseThrow();

        User customer = userRepository.findById(4L).orElseThrow();

        RepairCategory category = repairCategoryRepository.findById(createRequest.getCategoryId()).orElseThrow();

        ReceiptDelivery delivery = buildBy(createRequest.getReceiptDelivery(), receipt);

        Set<ReceiptItem> receiptItems = getReceiptItemSet(createRequest, receipt, currency);


        receipt.setUser(customer);

        receipt.setStatus(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow());

        receipt.setCategory(category);

        receipt.setPriceCurrency(currency);

        receipt.setItems(receiptItems);

        receipt.setDelivery(delivery);

        receipt.setNote(createRequest.getNote());

        receiptRepository.save(receipt);

        return receipt;
    }

    @Override
    @Transactional
    @Loggable
    public Receipt update(ReceiptUpdateRequest request) {
        Receipt receipt = receiptRepository.findById(request.getId()).orElseThrow();

        AppCurrency currency = appCurrencyRepository.findByCode(request.getPriceCurrency()).orElseThrow();

        Set<ReceiptItem> receiptItems = getReceiptItemSet(request, receipt, currency);

        receipt.setMaster(userRepository.findById(request.getMasterId()).orElseThrow());

        receipt.setStatus(receiptStatusRepository.findByName(request.getReceiptStatus()).orElseThrow());

        receipt.getItems().clear();
        receipt.getItems().addAll(receiptItems);

        receipt.setTotalPrice(getReceiptTotalPriceAmount(receiptItems));

        receipt.setPriceCurrency(currency);

        mergeDeliveryInto(receipt, buildBy(request.getReceiptDelivery(), receipt));

        receipt.setNote(request.getNote());

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return resultReceipt;
    }

    @Override
    @Loggable
    public ReceiptDTO constructDTO(Receipt receipt) {
        ReceiptDTO result = new ReceiptDTO();
        modelMapper.map(receipt, result);
        return result;
    }

    @Override
    public ReceiptDTO findById(Long id) {
        return constructDTO(receiptRepository.findById(id).orElseThrow());
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

    private Set<ReceiptItem> getReceiptItemSet(ReceiptUpdateRequest updateRequest, Receipt receipt, AppCurrency currency) {
        return updateRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt, currency))
                .collect(Collectors.toSet());
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptCreateRequest createRequest, Receipt receipt, AppCurrency currency) {
        return createRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt, currency))
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

    private ReceiptItem buildBy(ReceiptItemCreateRequest request, Receipt receipt, AppCurrency currency) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder().receipt(receipt).repairWork(repairWork).priceCurrency(currency).build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request, Receipt receipt, AppCurrency currency) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(currency)
                .build();
    }

    private BigDecimal getReceiptTotalPriceAmount(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
