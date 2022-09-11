package com.epam.finalproject.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequest {

    @NotNull
    private Long id;

    @Positive
    private BigDecimal moneyToAdd;


}
