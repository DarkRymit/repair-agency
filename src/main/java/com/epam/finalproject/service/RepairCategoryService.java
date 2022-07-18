package com.epam.finalproject.service;

import com.epam.finalproject.dto.RepairCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RepairCategoryService {
    Page<RepairCategoryDTO> findAll(Pageable pageable);
    RepairCategoryDTO findById(Long id);
    RepairCategoryDTO findByKeyName(String keyName);
}
