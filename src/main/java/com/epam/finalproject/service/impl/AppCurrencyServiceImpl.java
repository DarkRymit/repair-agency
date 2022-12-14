package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.repository.AppCurrencyRepository;
import com.epam.finalproject.service.AppCurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AppCurrencyServiceImpl implements AppCurrencyService {

    AppCurrencyRepository appCurrencyRepository;

    ModelMapper modelMapper;

    @Override
    public List<AppCurrency> findAll() {
        return appCurrencyRepository.findAll();
    }

    @Override
    public AppCurrency findByCode(String code) {
        return appCurrencyRepository.findByCode(code).orElseThrow();
    }
}
