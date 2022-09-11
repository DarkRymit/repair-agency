package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptStatusFlowService {
    List<ReceiptStatusFlowDTO> findAll();

    Page<ReceiptStatusFlowDTO> findAll(Pageable pageable);

    ReceiptStatusFlowDTO constructDTO(ReceiptStatusFlow flow);

    ReceiptStatusFlowDTO findById(Long id);

    List<ReceiptStatusFlowDTO> listAllAvailableForUser(String username);

    List<ReceiptStatusFlowDTO> listAllAvailableForUser(Long fromId,String username);

}
