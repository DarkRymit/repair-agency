package com.epam.finalproject.repository;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.entity.ReceiptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByUser_Username(String username);
    List<Receipt> findAllByMaster_Username(String username);
    List<Receipt> findAllByReceiptStatus_Name(ReceiptStatusEnum receiptStatusName);
    List<Receipt> findAllByTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Receipt> findAllByTimeAfter(LocalDateTime time);
    List<Receipt> findAllByTimeBefore(LocalDateTime time);

}
