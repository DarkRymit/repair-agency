package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.AppCurrencyDTO;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.repository.AppCurrencyRepository;
import com.epam.finalproject.service.AppCurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AppCurrencyServiceImpl implements AppCurrencyService {

    AppCurrencyRepository appCurrencyRepository;

    ModelMapper modelMapper;

    @Override
    public List<AppCurrencyDTO> findAll() {
        return appCurrencyRepository.findAll().stream().map(this::constructDTO).collect(Collectors.toList());
    }

    @Override
    public Page<AppCurrencyDTO> findAll(Pageable pageable) {
        return appCurrencyRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    @Loggable
    public AppCurrencyDTO constructDTO(AppCurrency currency) {
        AppCurrencyDTO result = new AppCurrencyDTO();
        modelMapper.map(currency, result);
        return result;
    }

    @Override
    public AppCurrencyDTO findById(Long id) {
        return constructDTO(appCurrencyRepository.findById(id).orElseThrow());
    }

    @Override
    public AppCurrencyDTO findByCode(String code) {
        return constructDTO(appCurrencyRepository.findByCode(code).orElseThrow());
    }
}
