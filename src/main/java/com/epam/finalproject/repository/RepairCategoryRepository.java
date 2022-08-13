package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.model.entity.RepairCategoryLocalPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairCategoryRepository extends JpaRepository<RepairCategory, Long> {
    Optional<RepairCategory> findByKeyName(String key);

    @Query("SELECT l FROM RepairCategory r join r.localParts l where r.id = :categoryId and l.language.lang = :lang")
    Optional<RepairCategoryLocalPart> findFirstLocalPartByCategory_IdAndLanguage_Lang(@Param("categoryId")Long categoryId,@Param("lang") String lang);

}
