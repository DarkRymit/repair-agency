package com.epam.finalproject.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class ReceiptCreateRequest {

    Long userID;

    Set<ReceiptItemCreateRequest> receiptItems;

    ReceiptDeliveryCreateRequest receiptDelivery;

    Long categoryId;

    String note;
}
