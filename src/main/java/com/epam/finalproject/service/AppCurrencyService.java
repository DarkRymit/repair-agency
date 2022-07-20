package com.epam.finalproject.service;

import com.epam.finalproject.dto.AppCurrencyDTO;
import com.epam.finalproject.model.entity.AppCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppCurrencyService {
    List<AppCurrencyDTO> findAll();

    Page<AppCurrencyDTO> findAll(Pageable pageable);

    AppCurrencyDTO constructDTO(AppCurrency currency);

    AppCurrencyDTO findById(Long id);

    AppCurrencyDTO findByCode(String code);
}
