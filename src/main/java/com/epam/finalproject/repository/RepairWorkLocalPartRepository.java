package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairWorkLocalPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairWorkLocalPartRepository extends JpaRepository<RepairWorkLocalPart, Long> {
    Optional<RepairWorkLocalPart> findByWork_IdAndLanguage_Lang(Long workId, String lang);
}
