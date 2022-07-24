package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.repository.RepairWorkRepository;
import com.epam.finalproject.service.RepairWorkService;
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
public class RepairWorkServiceImpl implements RepairWorkService {

    RepairWorkRepository repairWorkRepository;

    ModelMapper modelMapper;

    @Override
    public Page<RepairWorkDTO> findAll(Pageable pageable) {
        return repairWorkRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public RepairWorkDTO findById(Long id) {
        return constructDTO(repairWorkRepository.findById(id).orElseThrow());
    }

    @Override
    public List<RepairWorkDTO> findByKey(String key) {
        return repairWorkRepository.findByKeyName(key).stream().map(this::constructDTO).collect(Collectors.toList());
    }

    @Override
    public List<RepairWorkDTO> findByCategoryKey(String key) {
        return repairWorkRepository.findByCategoryKeyName(key)
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepairWorkDTO findByKeyAndCategoryKey(String workKey, String categoryKey) {
        return constructDTO(repairWorkRepository.findByKeyNameAndCategory_KeyName(workKey, categoryKey).orElseThrow());
    }

    @Loggable
    public RepairWorkDTO constructDTO(RepairWork repairWork) {
        return modelMapper.map(repairWork, RepairWorkDTO.class);
    }
}
