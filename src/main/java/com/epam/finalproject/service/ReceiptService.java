package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll();

    Page<Receipt> findAll(Pageable pageable);

    Receipt createNew(ReceiptCreateRequest createRequest);

    Receipt update(ReceiptUpdateRequest updateRequest);

    ReceiptDTO constructDTO(Receipt receipt);

    ReceiptDTO findById(Long id);
}
