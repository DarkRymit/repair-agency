package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairCategoryLocalPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairCategoryLocalPartRepository extends JpaRepository<RepairCategoryLocalPart, Long> {
    Optional<RepairCategoryLocalPart> findByCategory_IdAndLanguage_Lang(Long categoryId, String lang);
}
