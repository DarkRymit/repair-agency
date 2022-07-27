package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.ReceiptResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptResponseRepository extends JpaRepository<ReceiptResponse, Long> {
    List<ReceiptResponse> findAllByReceipt_Master_Username(String username);
    List<ReceiptResponse> findAllByReceipt_User_Username(String username);
    Integer countAllByReceipt_Master_Username(String username);

    Optional<ReceiptResponse> findByReceipt_Id(Long receiptId);
    boolean existsByReceipt_Id(Long id);

}
