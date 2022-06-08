package com.epam.finalproject.repository;

import com.epam.finalproject.entity.ReceiptStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {
}
