package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.AppCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppCurrencyRepository extends JpaRepository<AppCurrency, Long> {
    Optional<AppCurrency> findByCode(String code);

}
