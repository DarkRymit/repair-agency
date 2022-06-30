package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.enums.RepairCategoryName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        assertEquals(ReceiptStatusEnum.PAID,receipt.getReceiptStatus().getName());
        assertEquals("MasterStriker",receipt.getMaster().getUsername());

        Set<ReceiptItem> receiptItems = receipt.getReceiptItems();
        assertFalse(receiptItems.isEmpty());

        ReceiptDelivery receiptDelivery = receipt.getReceiptDelivery();
        assertNotNull(receiptDelivery);

        assertEquals(RepairCategoryName.NOTEBOOK,receipt.getCategory().getName());
        assertThat(BigDecimal.valueOf(84.9)).isEqualByComparingTo(receipt.getPriceAmount());
        assertEquals("USD",receipt.getPriceCurrency());
        assertEquals("Typical note",receipt.getNote());
        assertEquals(LocalDateTime.parse("2022-01-10T14:23:22"),receipt.getCreationTime());

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
        List<Receipt> receipts = receiptRepository.findAllByReceiptStatus_Name(ReceiptStatusEnum.PAID);
        assertFalse(receipts.isEmpty());
    }

    @Test
    void findAllByCreationTimeBetween() {
        List<Receipt> receipts = receiptRepository.findAllByCreationTimeBetween(LocalDateTime.parse("2022-01-10T13:23:22"),LocalDateTime.now());
        assertFalse(receipts.isEmpty());

    }

    @Test
    void findAllByCreationTimeAfter() {
        List<Receipt> receipts = receiptRepository.findAllByCreationTimeAfter(LocalDateTime.now());
        assertTrue(receipts.isEmpty());
    }

    @Test
    void findAllByCreationTimeBefore() {
        List<Receipt> receipts = receiptRepository.findAllByCreationTimeBefore(LocalDateTime.now());
        assertFalse(receipts.isEmpty());
    }
}