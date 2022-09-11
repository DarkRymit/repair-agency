package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.AppCurrency;

import java.util.List;

public interface AppCurrencyService {
    List<AppCurrency> findAll();

    AppCurrency findByCode(String code);
}
