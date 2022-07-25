package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairWorkPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairWorkPriceRepository extends JpaRepository<RepairWorkPrice, Long> {
    Optional<RepairWorkPrice> findByWork_IdAndCurrency_Code(Long workId, String code);
}
