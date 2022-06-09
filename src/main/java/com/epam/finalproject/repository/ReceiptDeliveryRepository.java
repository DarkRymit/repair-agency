package com.epam.finalproject.repository;

import com.epam.finalproject.entity.ReceiptDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptDeliveryRepository extends JpaRepository<ReceiptDelivery, Long> {
    List<ReceiptDelivery> findAllByReceipt_User_Username(String username);
}
