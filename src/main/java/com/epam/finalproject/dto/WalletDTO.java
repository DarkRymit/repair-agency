package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    private String name;

    private BigDecimal moneyAmount;

    private AppCurrency moneyCurrency;

}
