package com.epam.finalproject.repository;

import com.epam.finalproject.model.entity.AppLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppLocaleRepository extends JpaRepository<AppLocale, Long> {
    Optional<AppLocale> findByLang(String lang);

}
