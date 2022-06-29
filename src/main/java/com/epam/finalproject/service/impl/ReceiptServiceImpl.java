package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    UserRepository userRepository;

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
        receipt.setReceiptStatus(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow());
        receipt.setCategory(repairCategoryRepository.getById(createRequest.getCategoryId()));
        receipt.setReceiptItems(receiptItems);
        receipt.setPriceAmount(getReceiptTotalPriceAmount(receiptItems));
        receipt.setPriceCurrency(getReceiptPriceCurrency(receiptItems));
        receipt.setReceiptDelivery(buildBy(createRequest.getReceiptDelivery(), receipt));
        receipt.setNote(createRequest.getNote());
        receipt.setCreationTime(LocalDateTime.now());

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
        receipt.setReceiptStatus(receiptStatusRepository.findByName(request.getReceiptStatus()).orElseThrow());
        receipt.getReceiptItems().clear();
        receipt.getReceiptItems().addAll(receiptItems);
        receipt.setPriceAmount(getReceiptTotalPriceAmount(receiptItems));
        receipt.setPriceCurrency(getReceiptPriceCurrency(receiptItems));
        receipt.setReceiptDelivery(buildBy(request.getReceiptDelivery(), receipt));
        receipt.setNote(request.getNote());

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return resultReceipt;
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
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(request.getPriceCurrency())
                .build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request, Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(request.getPriceCurrency())
                .build();
    }

    private String getReceiptPriceCurrency(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceCurrency).distinct().collect(toSingleton());
    }

    private BigDecimal getReceiptTotalPriceAmount(Set<ReceiptItem> receiptItems) {
        return receiptItems.stream().map(ReceiptItem::getPriceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
