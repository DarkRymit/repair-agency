package com.epam.finalproject.service.impl;

import com.epam.finalproject.payload.request.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.ReceiptDeliveryCreateRequest;
import com.epam.finalproject.payload.request.ReceiptItemCreateRequest;
import com.epam.finalproject.entity.*;
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

        receipt.setPriceCurrency(receiptItems.stream().map(ReceiptItem::getPriceCurrency).distinct().collect(toSingleton()));
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

    private ReceiptDelivery getReceiptDelivery(ReceiptDeliveryCreateRequest deliveryCreateRequest, Receipt receipt) {
        ReceiptDelivery receiptDelivery = new ReceiptDelivery();

        receiptDelivery.setReceipt(receipt);

        receiptDelivery.setCity(deliveryCreateRequest.getCity());

        receiptDelivery.setCountry(deliveryCreateRequest.getCountry());

        receiptDelivery.setState(deliveryCreateRequest.getState());

        receiptDelivery.setLocalAddress(deliveryCreateRequest.getLocalAddress());

        receiptDelivery.setPostalCode(deliveryCreateRequest.getPostalCode());

        return receiptDelivery;
    }

    private ReceiptItem getReceiptItem(Receipt receipt, ReceiptItemCreateRequest e) {
        ReceiptItem receiptItem = new ReceiptItem();

        RepairWork repairWork = repairWorkRepository.getById(e.getRepairWorkID());

        receiptItem.setReceipt(receipt);

        receiptItem.setRepairWork(repairWork);

        receiptItem.setPriceAmount(e.getPriceAmount());

        receiptItem.setPriceCurrency(e.getPriceCurrency());

        return receiptItem;
    }
}
