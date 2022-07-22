package com.epam.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairWorkDTO {

    private Long id;

    private String keyName;

    private Set<RepairWorkStatusDTO> statuses;

    private String name;

    private AppLocaleDTO language;

    private BigDecimal lowerBorder;

    private BigDecimal upperBorder;

    private AppCurrencyDTO currency;

}
