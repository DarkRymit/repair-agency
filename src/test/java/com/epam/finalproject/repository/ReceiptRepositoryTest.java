package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import com.epam.finalproject.model.entity.ReceiptItem;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.ReceiptWithCustomerSearch;
import com.epam.finalproject.model.search.ReceiptWithMasterSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    void findByIdShouldReturnCorrectObjectWhenByExistId() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        assertEquals("CustomerStriker", receipt.getUser().getUsername());
        assertEquals(ReceiptStatusEnum.PAID, receipt.getStatus().getName());
        assertEquals("MasterStriker", receipt.getMaster().getUsername());

        Set<ReceiptItem> receiptItems = receipt.getItems();
        assertFalse(receiptItems.isEmpty());

        ReceiptDelivery receiptDelivery = receipt.getDelivery();
        assertNotNull(receiptDelivery);

        assertEquals("notebook", receipt.getCategory().getKeyName());
        assertThat(BigDecimal.valueOf(84.9)).isEqualByComparingTo(receipt.getTotalPrice());
        assertEquals("USD", receipt.getPriceCurrency().getCode());
        assertEquals("Typical note", receipt.getNote());
        assertEquals(Instant.parse("2022-01-10T14:23:22Z"), receipt.getCreationDate());

    }
    @Test
    void findByIdShouldReturnEqualObjectWhenMultipleByExistId() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        Receipt receipt1 = receiptRepository.findById(1L).orElseThrow();
        Receipt receipt2 = receiptRepository.findById(1L).orElseThrow();
        assertEquals(receipt.getId(),receipt1.getId());
        assertEquals(receipt1.getId(),receipt2.getId());
    }

    @Test
    void findAllShouldReturnNonEmptyListWhenSimpleFindAll() {
        List<Receipt> receipts = receiptRepository.findAll();
        assertFalse(receipts.isEmpty());
    }


    @Test
    void existsByIdShouldReturnTrueWhenExist() {
        assertTrue(receiptRepository.existsById(1L));
    }

    @Test
    void existsByIdShouldReturnTrueWhenNotExist() {
        assertFalse(receiptRepository.existsById(404L));
    }

    @Test
    void countShouldReturnLongGreaterThatOne() {
        assertTrue(receiptRepository.count() > 1);
    }

    @Test
    void findAllShouldReturnListSortedByIdASCWhenSortByIdASC() {
        List<Receipt> receipts = receiptRepository.findAll(Sort.by("id"));
        assertEquals(1, receipts.get(0).getId());
        assertEquals(2, receipts.get(1).getId());
        assertEquals(3, receipts.get(2).getId());
    }

    @Test
    void findAllShouldReturnListSortedByIdDESCWhenSortByIdDESC() {
        List<Receipt> receipts = receiptRepository.findAll(Sort.by("id").descending());
        assertEquals(3, receipts.get(receipts.size() - 3).getId());
        assertEquals(2, receipts.get(receipts.size() - 2).getId());
        assertEquals(1, receipts.get(receipts.size() - 1).getId());
    }

    @Test
    void findAllShouldReturnPageSortedByIdACSWithOffset2Sized2WhenByPageRequest() {
        Page<Receipt> receipts = receiptRepository.findAll(PageRequest.of(1, 2, Sort.by("id")));
        assertEquals(2, receipts.getSize());
        assertEquals(3, receipts.getContent().get(0).getId());
        assertEquals(4, receipts.getContent().get(1).getId());
    }


    @Test
    void saveDeliveryShouldSaveCorrectDelivery() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        ReceiptDelivery delivery = receipt.getDelivery();
        delivery.setCity("Lviv");
        receiptRepository.save(receipt);
        Receipt afterUpdate = receiptRepository.findById(1L).orElseThrow();
        assertEquals(delivery, afterUpdate.getDelivery());
        assertEquals("Lviv", afterUpdate.getDelivery().getCity());
    }

    @Test
    void deleteDeliveryShouldNotThrowException() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        receipt.setDelivery(null);
        assertDoesNotThrow(() -> receiptRepository.save(receipt));
    }

    @Test
    void saveItemShouldSaveCorrectItem() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        System.out.println(receipt.getItems());
        ReceiptItem item = receipt.getItems().stream().filter(i->i.getId().equals(1L)).findFirst().orElseThrow();
        BigDecimal newPrice = item.getPriceAmount().add(BigDecimal.TEN);
        item.setPriceAmount(newPrice);
        receiptRepository.save(receipt);
        Receipt afterUpdate = receiptRepository.findById(1L).orElseThrow();
        System.out.println(afterUpdate.getItems());
        ReceiptItem updatedItem = afterUpdate.getItems().stream().filter(i -> i.getPriceAmount().compareTo(item.getPriceAmount())==0).findFirst().orElseThrow();
        assertEquals(newPrice, updatedItem.getPriceAmount());
    }

    @Test
    void deleteItemShouldNotThrowException() {
        Receipt receipt = receiptRepository.findById(1L).orElseThrow();
        ReceiptItem item = receipt.getItems().stream().findFirst().orElseThrow();
        receipt.getItems().remove(item);
        assertDoesNotThrow(() -> receiptRepository.save(receipt));
    }
}