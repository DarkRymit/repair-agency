package com.epam.finalproject.repository;

import com.epam.finalproject.entity.RepairWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepairWorkRepository extends JpaRepository<RepairWork, Long> {
    List<RepairWork> findAllByParentID(RepairWork parentID);
    Optional<RepairWork> findByName(String name);
    List<RepairWork> findByParentIDIsNull();
}
