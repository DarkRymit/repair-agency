package com.epam.finalproject.dto;

import com.epam.finalproject.model.entity.AppCurrency;
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

    private BigDecimal lowerBorder;

    private BigDecimal upperBorder;

    private AppCurrency currency;

}
