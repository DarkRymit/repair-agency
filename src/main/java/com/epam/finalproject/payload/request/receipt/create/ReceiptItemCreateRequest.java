package com.epam.finalproject.payload.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemCreateRequest {
    Long repairWorkID;

    BigDecimal priceAmount;

    String priceCurrency;
}
