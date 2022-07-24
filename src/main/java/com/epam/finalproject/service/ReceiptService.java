package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptService {
    List<ReceiptDTO> findAll();

    Page<ReceiptDTO> findAll(Pageable pageable);

    ReceiptDTO createNew(ReceiptCreateRequest createRequest, String username);

    ReceiptDTO update(ReceiptUpdateRequest updateRequest);

    ReceiptDTO updateStatus(Long receiptId,Long statusId, String username);

    ReceiptDTO constructDTO(Receipt receipt);

    ReceiptDTO findById(Long id);
}
