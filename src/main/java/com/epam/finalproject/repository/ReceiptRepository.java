package com.epam.finalproject.repository;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.entity.ReceiptStatus;
import com.epam.finalproject.entity.ReceiptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByUser_Username(String username);
    List<Receipt> findAllByMaster_Username(String username);
    List<Receipt> findAllByReceiptStatus_Name(ReceiptStatusEnum receiptStatus_name);
    List<Receipt> findAllByTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Receipt> findAllByTimeAfter(LocalDateTime time);
    List<Receipt> findAllByTimeBefore(LocalDateTime time);

}
