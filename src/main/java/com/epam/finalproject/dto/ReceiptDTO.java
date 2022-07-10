package com.epam.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {

    private Long id;

    private UserDTO user;

    private ReceiptStatusDTO status;

    private UserDTO master;

    private Set<ReceiptItemDTO> items;

    private ReceiptDeliveryDTO delivery;

    private RepairCategoryDTO category;

    private BigDecimal totalPrice;

    private AppCurrencyDTO priceCurrency;

    private String note;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="EEE MMM dd HH:mm:ss Z yyyy")
    private ZonedDateTime creationDate;

    private String createdBy;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

}
