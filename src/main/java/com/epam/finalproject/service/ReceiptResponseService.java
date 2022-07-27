package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.model.entity.ReceiptResponse;
import com.epam.finalproject.payload.request.ReceiptResponseCreateRequest;

import java.util.List;

public interface ReceiptResponseService {
    List<ReceiptResponseDTO> findAll();

    ReceiptResponseDTO createNew(ReceiptResponseCreateRequest createRequest, String username);

    ReceiptResponseDTO constructDTO(ReceiptResponse receiptResponse);

    ReceiptResponseDTO findById(Long id);

    ReceiptResponseDTO findByReceiptId(Long id);
    boolean existByReceiptId(Long id);
}
