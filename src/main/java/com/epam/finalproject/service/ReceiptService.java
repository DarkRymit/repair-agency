package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;

public interface ReceiptService {

    ReceiptDTO createNew(ReceiptCreateRequest createRequest, String username);

    ReceiptDTO update(ReceiptUpdateRequest updateRequest);

    ReceiptDTO updateStatus(Long receiptId,Long statusId, String username);

    ReceiptDTO constructDTO(Receipt receipt);

    ReceiptDTO findById(Long id);

    ReceiptDTO pay(ReceiptPayRequest payRequest, String username);
}
