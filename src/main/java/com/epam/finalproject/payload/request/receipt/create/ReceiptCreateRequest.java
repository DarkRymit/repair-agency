package com.epam.finalproject.payload.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptCreateRequest {

    @NotNull
    Long categoryId;

    @NotBlank
    String priceCurrency;

    @Valid
    Set<ReceiptItemCreateRequest> receiptItems;

    @Valid
    ReceiptDeliveryCreateRequest receiptDelivery;


    String note;
}
