package com.epam.finalproject.service;

import com.epam.finalproject.dto.RepairWorkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairWorkService {
    Page<RepairWorkDTO> findAll(Pageable pageable);
    RepairWorkDTO findById(Long id);
    List<RepairWorkDTO> findByKey(String key);
    List<RepairWorkDTO> findByCategoryKey(String key);
    RepairWorkDTO findByKeyAndCategoryKey(String workKey, String categoryKey);
}
