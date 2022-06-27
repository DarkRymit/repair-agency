package com.epam.finalproject.payload.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItemCreateRequest {
    Long repairWorkID;

    BigDecimal priceAmount;

    String priceCurrency;
}
