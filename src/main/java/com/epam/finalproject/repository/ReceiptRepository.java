package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt> {
    List<Receipt> findAllByUser_Username(String username);
    List<Receipt> findAllByMaster_Username(String username);
    List<Receipt> findAllByReceiptStatus_Name(ReceiptStatusEnum receiptStatusName);
    List<Receipt> findAllByCreationTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Receipt> findAllByCreationTimeAfter(LocalDateTime time);
    List<Receipt> findAllByCreationTimeBefore(LocalDateTime time);
}
