package com.epam.finalproject.repository.specification;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.repository.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
@ActiveProfiles("test")
class ReceiptSpecificationsTest {

    @Autowired
    ReceiptRepository receiptRepository;

    @Test
    void hasStatusWithName() {
        List<Receipt> receipts = receiptRepository.findAll(ReceiptSpecifications.hasStatusWithName(Set.of(ReceiptStatusEnum.DONE)));
        assertFalse(receipts.isEmpty());
    }
    @Test
    void hasStatusWithNameSetEmpty() {
        Set<ReceiptStatusEnum> set = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> ReceiptSpecifications.hasStatusWithName(set));
    }
    @Test
    void hasStatusWithNameSetNull() {
        Set<ReceiptStatusEnum> set = null;
        assertThrows(NullPointerException.class, () -> ReceiptSpecifications.hasStatusWithName(set));
    }


    @Test
    void hasMasterWithNameLike() {
        List<Receipt> receipts = receiptRepository.findAll(ReceiptSpecifications.hasMasterWithNameLike("Master"));
        assertFalse(receipts.isEmpty());
    }
    @Test
    void hasMasterWithNameLikeBlankString() {
        String name = "";
        assertThrows(IllegalArgumentException.class, () -> ReceiptSpecifications.hasMasterWithNameLike(name));
    }
    @Test
    void hasMasterWithNameLikeNull() {
        String name = null;
        assertThrows(NullPointerException.class, () -> ReceiptSpecifications.hasMasterWithNameLike(name));
    }

    @Test
    void hasUserWithNameLike() {
        List<Receipt> receipts = receiptRepository.findAll(ReceiptSpecifications.hasUserWithNameLike("Customer"));
        assertFalse(receipts.isEmpty());
    }
    @Test
    void hasUserWithNameLikeBlankString() {
        String name = "";
        assertThrows(IllegalArgumentException.class, () -> ReceiptSpecifications.hasUserWithNameLike(name));
    }
    @Test
    void hasUserWithNameLikeNull() {
        String name = null;
        assertThrows(NullPointerException.class, () -> ReceiptSpecifications.hasUserWithNameLike(name));
    }

    @Test
    void matchSearch() {
        ReceiptSearch search = ReceiptSearch.builder()
                .userUsername("Customer")
                .masterUsername("Master")
                .receiptStatuses(Set.of(ReceiptStatusEnum.DONE,ReceiptStatusEnum.CREATED))
                .build();
        List<Receipt> receipts = receiptRepository.findAll(ReceiptSpecifications.matchSearch(search));
        assertFalse(receipts.isEmpty());
    }
}