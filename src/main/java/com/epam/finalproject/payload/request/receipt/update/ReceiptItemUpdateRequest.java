package com.epam.finalproject.payload.request.receipt.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemUpdateRequest {
    @NotNull
    Long repairWorkID;
    @PositiveOrZero
    BigDecimal priceAmount;
}
