package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.model.entity.ReceiptResponse;
import com.epam.finalproject.payload.request.ReceiptResponseCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptResponseService {
    List<ReceiptResponseDTO> findAll();
    Page<ReceiptResponseDTO> findAll(Pageable pageable);

    ReceiptResponseDTO createNew(ReceiptResponseCreateRequest createRequest, String username);

    ReceiptResponseDTO constructDTO(ReceiptResponse receiptResponse);

    ReceiptResponseDTO findById(Long id);

    ReceiptResponseDTO findByReceiptId(Long id);
    boolean existByReceiptId(Long id);

    Page<ReceiptResponseDTO> findByCustomerUsername(String username, Pageable pageable);
    Page<ReceiptResponseDTO> findByMasterUsername(String username, Pageable pageable);
}
