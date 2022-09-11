package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.Receipt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt> {
    @Override
    @EntityGraph(attributePaths = {"user","master","status","items","category","items"})
    @NonNull Optional<Receipt> findById(@NonNull Long aLong);
}
