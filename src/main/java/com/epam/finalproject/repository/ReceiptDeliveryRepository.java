package com.epam.finalproject.repository;

import com.epam.finalproject.entity.ReceiptDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptDeliveryRepository extends JpaRepository<ReceiptDelivery, Long> {
    List<ReceiptDelivery> findAllByReceipt_User_Username(String username);
}
