package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptStatusFlowRepository extends JpaRepository<ReceiptStatusFlow, Long> {
    List<ReceiptStatusFlow> findAllByFromAndTo(ReceiptStatus from, ReceiptStatus to);
    Optional<ReceiptStatusFlow> findByFromAndToAndRole_Name(ReceiptStatus from, ReceiptStatus to, RoleEnum name);
}
