package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest()
@ActiveProfiles("test")
class ReceiptStatusRepositoryTest {

    @Autowired
    ReceiptStatusRepository receiptStatusRepository;

    @Test
    void findAll() {
        List<ReceiptStatus> receiptStatuses = receiptStatusRepository.findAll();
        assertFalse(receiptStatuses.isEmpty());
    }

    @Test
    void findByName() {
        ReceiptStatus receiptStatus = receiptStatusRepository.findByName(ReceiptStatusEnum.DONE).orElseThrow();
        assertEquals(ReceiptStatusEnum.DONE, receiptStatus.getName());
    }

}