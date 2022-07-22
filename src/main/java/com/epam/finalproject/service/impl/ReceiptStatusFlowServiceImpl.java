package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.ReceiptStatusFlowRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.ReceiptStatusFlowService;
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
public class ReceiptStatusFlowServiceImpl implements ReceiptStatusFlowService {

    ReceiptStatusFlowRepository receiptStatusFlowRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;


    @Override
    public List<ReceiptStatusFlowDTO> findAll() {
        return receiptStatusFlowRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReceiptStatusFlowDTO> findAll(Pageable pageable) {
        return receiptStatusFlowRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    @Loggable
    public ReceiptStatusFlowDTO constructDTO(ReceiptStatusFlow flow) {
        ReceiptStatusFlowDTO result = new ReceiptStatusFlowDTO();
        modelMapper.map(flow, result);
        log.info(result.toString());
        return result;
    }

    @Override
    public ReceiptStatusFlowDTO findById(Long id) {
        return constructDTO(receiptStatusFlowRepository.findById(id).orElseThrow());
    }

    @Override
    public ReceiptStatusFlowDTO findByStatusesFromToAndRoleEnum(ReceiptStatus from, ReceiptStatus to, RoleEnum name) {
        return constructDTO(receiptStatusFlowRepository.findByFromStatusAndToStatusAndRole_Name(from, to, name)
                .orElseThrow());
    }

    @Override
    @Loggable
    public List<ReceiptStatusFlowDTO> listAllAvailableForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<ReceiptStatusFlow> flows = receiptStatusFlowRepository.findDistinctByRoleIn(user.getRoles());
        return flows.stream().map(this::constructDTO).collect(Collectors.toList());
    }
}
