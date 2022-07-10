package com.epam.finalproject.service.impl;

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

import static com.epam.finalproject.util.CustomCollectionsUtil.toSingleton;

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
    public Receipt createNew(ReceiptCreateRequest createRequest) {
        log.info(createRequest.toString());

        Receipt receipt = new Receipt();

        Set<ReceiptItem> receiptItems = getReceiptItemSet(createRequest, receipt);

        receipt.setUser(userRepository.findById(createRequest.getUserID()).orElseThrow());
        receipt.setStatus(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow());
        receipt.setCategory(repairCategoryRepository.getById(createRequest.getCategoryId()));
        receipt.setItems(receiptItems);
        receipt.setTotalPrice(getReceiptTotalPriceAmount(receiptItems));
        receipt.setPriceCurrency(getReceiptPriceCurrency(receiptItems));
        receipt.setDelivery(buildBy(createRequest.getReceiptDelivery(), receipt));
        receipt.setNote(createRequest.getNote());

        receiptRepository.save(receipt);
        log.info("save receipt");

        return receipt;
    }

    @Override
    @Transactional
    public Receipt update(ReceiptUpdateRequest request) {
        log.info(request.toString());

        Receipt receipt = receiptRepository.findById(request.getId()).orElseThrow();

        Set<ReceiptItem> receiptItems = getReceiptItemSet(request, receipt);

        receipt.setMaster(userRepository.findById(request.getMasterId()).orElseThrow());
        receipt.setStatus(receiptStatusRepository.findByName(request.getReceiptStatus()).orElseThrow());
        receipt.getItems().clear();
        receipt.getItems().addAll(receiptItems);
        receipt.setTotalPrice(getReceiptTotalPriceAmount(receiptItems));
        receipt.setPriceCurrency(getReceiptPriceCurrency(receiptItems));
        mergeDeliveryInto(receipt,buildBy(request.getReceiptDelivery(), receipt));
        receipt.setNote(request.getNote());

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return resultReceipt;
    }

    @Override
    public ReceiptDTO constructDTO(Receipt receipt) {
        log.info("Arg :" + receipt);
        ReceiptDTO result = new ReceiptDTO();
        modelMapper.map(receipt,result);
        log.info("ReceiptDTO constructed :" + result);
        return result;
    }

    private void mergeDeliveryInto(Receipt receipt, ReceiptDelivery delivery) {
        ReceiptDelivery oldDelivery = receipt.getDelivery();
        if (!oldDelivery.equals(delivery)){
            oldDelivery.setCity(delivery.getCity());
            oldDelivery.setCountry(delivery.getCountry());
            oldDelivery.setState(delivery.getState());
            oldDelivery.setLocalAddress(delivery.getLocalAddress());
            oldDelivery.setPostalCode(delivery.getPostalCode());
        }
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptUpdateRequest updateRequest, Receipt receipt) {
        return updateRequest.getReceiptItems().stream().map(e -> buildBy(e, receipt)).collect(Collectors.toSet());
    }

    private Set<ReceiptItem> getReceiptItemSet(ReceiptCreateRequest createRequest, Receipt receipt) {
        return createRequest.getReceiptItems().stream().map(e -> buildBy(e, receipt)).collect(Collectors.toSet());
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
        AppCurrency priceCurrency = appCurrencyRepository.findByCode(request.getPriceCurrency()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(priceCurrency)
                .build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        AppCurrency priceCurrency = appCurrencyRepository.findByCode(request.getPriceCurrency()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(priceCurrency)
                .build();
    }

    private AppCurrency getReceiptPriceCurrency(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceCurrency).distinct().collect(toSingleton());
    }

    private BigDecimal getReceiptTotalPriceAmount(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
