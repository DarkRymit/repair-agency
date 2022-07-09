package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairWorkRepository extends JpaRepository<RepairWork, Long> {
    List<RepairWork> findByKeyName(String key);
    Optional<RepairWork> findByKeyNameAndCategory_KeyName(String workKey, String categoryKey);
}
