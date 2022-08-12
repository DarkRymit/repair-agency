package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.AppCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppCurrencyService {
    List<AppCurrency> findAll();

    Page<AppCurrency> findAll(Pageable pageable);

    AppCurrency findById(Long id);

    AppCurrency findByCode(String code);
}
