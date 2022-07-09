package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import com.epam.finalproject.model.entity.ReceiptItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@ActiveProfiles("test")
class ReceiptRepositoryTest {

    @Autowired
    ReceiptRepository receiptRepository;

    @Test
    void find() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        assertEquals("CustomerStriker",receipt.getUser().getUsername());
        assertEquals(ReceiptStatusEnum.PAID,receipt.getStatus().getName());
        assertEquals("MasterStriker",receipt.getMaster().getUsername());

        Set<ReceiptItem> receiptItems = receipt.getItems();
        assertFalse(receiptItems.isEmpty());

        ReceiptDelivery receiptDelivery = receipt.getDelivery();
        assertNotNull(receiptDelivery);

        assertEquals("notebook",receipt.getCategory().getKeyName());
        assertThat(BigDecimal.valueOf(84.9)).isEqualByComparingTo(receipt.getTotalPrice());
        assertEquals("USD",receipt.getPriceCurrency().getCode());
        assertEquals("Typical note",receipt.getNote());
        assertEquals(Instant.parse("2022-01-10T14:23:22Z"),receipt.getCreationDate());

    }

    @Test
    void findAll() {
        List<Receipt> receipts = receiptRepository.findAll();
        assertFalse(receipts.isEmpty());
    }

    @Test
    void findAllByUser_Username() {
        List<Receipt> receipts = receiptRepository.findAllByUser_Username("CustomerStriker");
        assertFalse(receipts.isEmpty());
    }

    @Test
    void findAllByMaster_Username() {
        List<Receipt> receipts = receiptRepository.findAllByMaster_Username("MasterStriker");
        assertFalse(receipts.isEmpty());
    }

    @Test
    void findAllByReceiptStatus_Name() {
        List<Receipt> receipts = receiptRepository.findAllByStatus_Name(ReceiptStatusEnum.PAID);
        assertFalse(receipts.isEmpty());
    }

    @Test
    void findAllByCreationTimeBetween() {
        List<Receipt> receipts = receiptRepository.findAllByCreationDateBetween(Instant.parse("2022-01-10T13:23:22Z"),Instant.now());
        assertFalse(receipts.isEmpty());

    }

    @Test
    void findAllByCreationTimeAfter() {
        List<Receipt> receipts = receiptRepository.findAllByCreationDateAfter(Instant.now());
        assertTrue(receipts.isEmpty());
    }

    @Test
    void findAllByCreationTimeBefore() {
        List<Receipt> receipts = receiptRepository.findAllByCreationDateBefore(Instant.now());
        assertFalse(receipts.isEmpty());
    }
}