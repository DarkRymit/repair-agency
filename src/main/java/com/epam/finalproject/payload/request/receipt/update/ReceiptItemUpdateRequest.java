package com.epam.finalproject.payload.request.receipt.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemUpdateRequest {
    Long repairWorkID;

    BigDecimal priceAmount;
}
