package com.epam.finalproject.repository;

import com.epam.finalproject.entity.RepairWorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RepairWorkStatusRepository extends JpaRepository<RepairWorkStatus, Long> {
    Optional<RepairWorkStatus> findByName(String name);
}
