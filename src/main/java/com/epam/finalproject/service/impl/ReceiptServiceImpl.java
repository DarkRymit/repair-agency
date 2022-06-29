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
        log.info("create receipt");

        User user = userRepository.findById(createRequest.getUserID()).orElseThrow();
        receipt.setUser(user);
        log.info("set user");

        ReceiptStatus receiptStatus = receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED).orElseThrow();
        receipt.setReceiptStatus(receiptStatus);
        log.info("set status");

        RepairCategory repairCategory = repairCategoryRepository.getById(createRequest.getCategoryId());
        receipt.setCategory(repairCategory);
        log.info("set category");

        Set<ReceiptItem> receiptItems = createRequest.getReceiptItems()
                .stream()
                .map(e -> getReceiptItem(receipt, e))
                .collect(Collectors.toSet());
        receipt.setReceiptItems(receiptItems);
        log.info("set receiptItems");

        receipt.setPriceAmount(receiptItems.stream()
                .map(ReceiptItem::getPriceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        log.info("set PriceAmount");

        receipt.setPriceCurrency(receiptItems.stream()
                .map(ReceiptItem::getPriceCurrency)
                .distinct()
                .collect(toSingleton()));
        log.info("set PriceCurrency");

        ReceiptDelivery receiptDelivery = getReceiptDelivery(createRequest.getReceiptDelivery(), receipt);
        receipt.setReceiptDelivery(receiptDelivery);
        log.info("set ReceiptDelivery");

        receipt.setNote(createRequest.getNote());
        log.info("set Note");

        receipt.setCreationTime(LocalDateTime.now());
        log.info("set CreationTime");

        receiptRepository.save(receipt);
        log.info("Save Receipt");

        return receipt;
    }

    @Override
    public Receipt update(ReceiptUpdateRequest updateRequest) {

        log.info(updateRequest.toString());

        Receipt receipt = receiptRepository.findById(updateRequest.getId()).orElseThrow();

        User user = userRepository.findById(updateRequest.getMasterId()).orElseThrow();
        receipt.setMaster(user);
        log.info("update master");

        ReceiptStatus receiptStatus = receiptStatusRepository.findByName(updateRequest.getReceiptStatus())
                .orElseThrow();
        receipt.setReceiptStatus(receiptStatus);
        log.info("update status");

        Set<ReceiptItem> receiptItems = updateRequest.getReceiptItems()
                .stream()
                .map(e -> getReceiptItem(receipt, e))
                .collect(Collectors.toSet());

        receipt.getReceiptItems().clear();
        receipt.getReceiptItems().addAll(receiptItems);

        log.info("update receiptItems");

        receipt.setPriceAmount(receiptItems.stream()
                .map(ReceiptItem::getPriceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        log.info("update PriceAmount");

        receipt.setPriceCurrency(receiptItems.stream()
                .map(ReceiptItem::getPriceCurrency)
                .distinct()
                .collect(toSingleton()));
        log.info("update PriceCurrency");

        ReceiptDelivery receiptDelivery = getReceiptDelivery(updateRequest.getReceiptDelivery(), receipt);
        receipt.setReceiptDelivery(receiptDelivery);
        log.info("update ReceiptDelivery");

        receipt.setNote(updateRequest.getNote());
        log.info("update Note");

        Receipt resultReceipt = receiptRepository.save(receipt);
        log.info("save receipt");

        return resultReceipt;
    }

    private ReceiptDelivery getReceiptDelivery(ReceiptDeliveryCreateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .receipt(receipt)
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptDelivery getReceiptDelivery(ReceiptDeliveryUpdateRequest request, Receipt receipt) {
        return ReceiptDelivery.builder()
                .receipt(receipt)
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .localAddress(request.getLocalAddress())
                .postalCode(request.getPostalCode())
                .build();
    }

    private ReceiptItem getReceiptItem(Receipt receipt, ReceiptItemCreateRequest e) {
        RepairWork repairWork = repairWorkRepository.findById(e.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(e.getPriceAmount())
                .priceCurrency(e.getPriceCurrency())
                .build();
    }

    private ReceiptItem getReceiptItem(Receipt receipt, ReceiptItemUpdateRequest e) {
        RepairWork repairWork = repairWorkRepository.findById(e.getRepairWorkID()).orElseThrow();
        return ReceiptItem.builder()
                .receipt(receipt)
                .repairWork(repairWork)
                .priceAmount(e.getPriceAmount())
                .priceCurrency(e.getPriceCurrency())
                .build();
    }
}
