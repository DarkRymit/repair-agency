package com.epam.finalproject.repository;

import com.epam.finalproject.entity.RepairCategoryName;
import com.epam.finalproject.entity.RepairWork;
import com.epam.finalproject.entity.RepairWorkName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairWorkRepository extends JpaRepository<RepairWork, Long> {
    List<RepairWork> findByName(RepairWorkName name);
    Optional<RepairWork> findByNameAndCategory_Name(RepairWorkName name, RepairCategoryName categoryName);
}
