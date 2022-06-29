package com.epam.finalproject.payload.request.receipt.update;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItemUpdateRequest {
    Long repairWorkID;

    BigDecimal priceAmount;

    String priceCurrency;
}
