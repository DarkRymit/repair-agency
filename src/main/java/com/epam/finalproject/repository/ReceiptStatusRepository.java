package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {
    Optional<ReceiptStatus> findByName(ReceiptStatusEnum name);

}
