package com.epam.finalproject.payload.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptCreateRequest {

    Long categoryId;

    String priceCurrency;

    Set<ReceiptItemCreateRequest> receiptItems;

    ReceiptDeliveryCreateRequest receiptDelivery;

    String note;
}
