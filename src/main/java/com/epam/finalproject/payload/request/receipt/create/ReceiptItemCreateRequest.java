package com.epam.finalproject.payload.request.receipt.create;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItemCreateRequest {
    Long repairWorkID;

    BigDecimal priceAmount;

    String priceCurrency;
}
