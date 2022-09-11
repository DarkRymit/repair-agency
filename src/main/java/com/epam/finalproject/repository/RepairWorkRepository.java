package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.model.entity.RepairWorkLocalPart;
import com.epam.finalproject.model.entity.RepairWorkPrice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairWorkRepository extends JpaRepository<RepairWork, Long> {
    List<RepairWork> findByKeyName(String key);
    List<RepairWork> findByCategoryKeyName(String key);

    @EntityGraph(attributePaths = {"category"})
    Optional<RepairWork> findByKeyNameAndCategory_KeyName(String workKey, String categoryKey);

    @Query("SELECT l FROM RepairWork r join r.localParts l where r.id = :workId and l.language.lang = :lang")
    Optional<RepairWorkLocalPart> findLocalByWork_IdAndLanguage_Lang(@Param("workId")Long workId, @Param("lang")String lang);

    @Query("SELECT p FROM RepairWork r join r.prices p where r.id = :workId and p.currency.code = :code")
    Optional<RepairWorkPrice> findPriceByWork_IdAndCurrency_Code(@Param("workId")Long workId,  @Param("code")String code);
}
