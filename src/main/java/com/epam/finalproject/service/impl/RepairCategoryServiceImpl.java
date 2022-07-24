package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.repository.RepairCategoryRepository;
import com.epam.finalproject.service.RepairCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RepairCategoryServiceImpl implements RepairCategoryService {

    RepairCategoryRepository repairCategoryRepository;

    ModelMapper modelMapper;

    @Override
    public Page<RepairCategoryDTO> findAll(Pageable pageable) {
        return repairCategoryRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public List<RepairCategoryDTO> findAll() {
        return repairCategoryRepository.findAll(Sort.by("keyName"))
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepairCategoryDTO findById(Long id) {
        return constructDTO(repairCategoryRepository.findById(id).orElseThrow());
    }

    @Override
    public RepairCategoryDTO findByKeyName(String keyName) {
        return constructDTO(repairCategoryRepository.findByKeyName(keyName).orElseThrow());
    }

    @Loggable
    public RepairCategoryDTO constructDTO(RepairCategory repairCategory) {
        return modelMapper.map(repairCategory,RepairCategoryDTO.class);
    }
}
