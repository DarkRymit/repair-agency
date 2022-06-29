package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.*;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    ReceiptRepository receiptRepository;

    ReceiptStatusRepository receiptStatusRepository;

    RepairCategoryRepository repairCategoryRepository;

    RepairWorkRepository repairWorkRepository;

    UserRepository userRepository;

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            if (list.size() != 1) {
                throw new IllegalStateException();
            }
            return list.get(0);
        });
    }

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

        User user = userRepository.findById(createRequest.getUserID()).orElseThrow();
        ReceiptStatus receiptStatus = receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow();
        RepairCategory repairCategory = repairCategoryRepository.getById(createRequest.getCategoryId());
        Set<ReceiptItem> receiptItems = createRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt))
                .collect(Collectors.toSet());
        ReceiptDelivery receiptDelivery = buildBy(createRequest.getReceiptDelivery(), receipt);
        BigDecimal receiptTotalPriceAmount = receiptItems.stream()
                .map(ReceiptItem::getPriceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String receiptPriceCurrency = receiptItems.stream()
                .map(ReceiptItem::getPriceCurrency)
                .distinct()
                .collect(toSingleton());

        receipt.setUser(user);
        receipt.setReceiptStatus(receiptStatus);
        receipt.setCategory(repairCategory);
        receipt.setReceiptItems(receiptItems);
        receipt.setPriceAmount(receiptTotalPriceAmount);
        receipt.setPriceCurrency(receiptPriceCurrency);
        receipt.setReceiptDelivery(receiptDelivery);
        receipt.setNote(createRequest.getNote());
        receipt.setCreationTime(LocalDateTime.now());

        receiptRepository.save(receipt);
        log.info("save receipt");

        return receipt;
    }

    @Override
    public Receipt update(ReceiptUpdateRequest updateRequest) {

        log.info(updateRequest.toString());

        Receipt receipt = receiptRepository.findById(updateRequest.getId()).orElseThrow();

        User user = userRepository.findById(updateRequest.getMasterId()).orElseThrow();
        ReceiptStatus receiptStatus = receiptStatusRepository.findByName(updateRequest.getReceiptStatus())
                .orElseThrow();
        Set<ReceiptItem> receiptItems = updateRequest.getReceiptItems()
                .stream()
                .map(e -> buildBy(e, receipt))
                .collect(Collectors.toSet());
        BigDecimal receiptTotalPriceAmount = receiptItems.stream()
                .map(ReceiptItem::getPriceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String receiptPriceCurrency = receiptItems.stream()
                .map(ReceiptItem::getPriceCurrency)
                .distinct()
                .collect(toSingleton());
        ReceiptDelivery receiptDelivery = buildBy(updateRequest.getReceiptDelivery(), receipt);

        receipt.setMaster(user);
        receipt.setReceiptStatus(receiptStatus);
        receipt.getReceiptItems().clear();
        receipt.getReceiptItems().addAll(receiptItems);
        receipt.setPriceAmount(receiptTotalPriceAmount);
        receipt.setPriceCurrency(receiptPriceCurrency);
        receipt.setReceiptDelivery(receiptDelivery);
        receipt.setNote(updateRequest.getNote());

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return resultReceipt;
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

    private ReceiptItem buildBy(ReceiptItemCreateRequest request,Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(request.getPriceCurrency())
                .build();
    }

    private ReceiptItem buildBy(ReceiptItemUpdateRequest request,Receipt receipt) {
        RepairWork repairWork = repairWorkRepository.findById(request.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(request.getPriceAmount())
                .priceCurrency(request.getPriceCurrency())
                .build();
    }
}
