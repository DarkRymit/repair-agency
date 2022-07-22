package com.epam.finalproject.service;

import com.epam.finalproject.dto.RepairCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairCategoryService {
    Page<RepairCategoryDTO> findAll(Pageable pageable);
    List<RepairCategoryDTO> findAll();
    RepairCategoryDTO findById(Long id);
    RepairCategoryDTO findByKeyName(String keyName);
}
