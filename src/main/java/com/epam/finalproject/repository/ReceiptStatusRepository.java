package com.epam.finalproject.repository;

import com.epam.finalproject.entity.ReceiptStatus;
import com.epam.finalproject.entity.ReceiptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {
    Optional<ReceiptStatus> findByName(ReceiptStatusEnum name);

}
