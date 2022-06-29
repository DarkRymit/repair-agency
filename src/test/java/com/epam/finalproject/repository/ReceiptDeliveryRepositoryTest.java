package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class ReceiptDeliveryRepositoryTest {

    @Autowired
    ReceiptDeliveryRepository receiptDeliveryRepository;

    @Test
    void find() {
        ReceiptDelivery receiptDeliveries = receiptDeliveryRepository.findById(1L).orElseThrow();
        Receipt receipt = receiptDeliveries.getReceipt();
        assertNotNull(receipt);
        assertEquals(1L,receipt.getReceiptDelivery().getId());
        assertEquals("Kyiv",receiptDeliveries.getCity());
        assertEquals("Ukraine",receiptDeliveries.getCountry());
        assertEquals("some street2",receiptDeliveries.getLocalAddress());
    }

    @Test
    void findAll() {
        List<ReceiptDelivery> receiptDeliveries = receiptDeliveryRepository.findAll();
        assertFalse(receiptDeliveries.isEmpty());
    }

    @Test
    void findAllByReceipt_User_Username() {
        List<ReceiptDelivery> receiptDeliveries = receiptDeliveryRepository.findAllByReceipt_User_Username("CustomerStriker");
        assertFalse(receiptDeliveries.isEmpty());
    }
}