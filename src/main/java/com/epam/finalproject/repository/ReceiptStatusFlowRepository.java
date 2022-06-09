package com.epam.finalproject.repository;

import com.epam.finalproject.entity.ReceiptStatus;
import com.epam.finalproject.entity.ReceiptStatusFlow;
import com.epam.finalproject.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptStatusFlowRepository extends JpaRepository<ReceiptStatusFlow, Long> {
    List<ReceiptStatusFlow> findAllByFromAndTo(ReceiptStatus from, ReceiptStatus to);
    Optional<ReceiptStatusFlow> findByFromAndToAndRole_Name(ReceiptStatus from, ReceiptStatus to, RoleEnum name);
}
