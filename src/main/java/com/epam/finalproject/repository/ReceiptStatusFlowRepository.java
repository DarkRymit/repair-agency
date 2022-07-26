package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReceiptStatusFlowRepository extends JpaRepository<ReceiptStatusFlow, Long> {
    List<ReceiptStatusFlow> findAllByFromStatusAndToStatus(ReceiptStatus from, ReceiptStatus to);

    List<ReceiptStatusFlow> findDistinctByRoleIn(Set<Role> role);
    List<ReceiptStatusFlow> findDistinctByFromStatus_IdAndRoleIn(Long fromId,Set<Role> role);

    Optional<ReceiptStatusFlow> findByFromStatusAndToStatusAndRole_Name(ReceiptStatus from, ReceiptStatus to, RoleEnum name);

    boolean existsByFromStatusAndToStatusAndRoleIn(ReceiptStatus fromStatus, ReceiptStatus toStatus, Collection<Role> role);
}
