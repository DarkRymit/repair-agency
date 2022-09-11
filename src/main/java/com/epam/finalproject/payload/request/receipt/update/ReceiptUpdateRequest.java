package com.epam.finalproject.payload.request.receipt.update;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ReceiptUpdateRequest {

    @JsonIgnore
    Long id;

    @NotNull
    ReceiptStatusEnum receiptStatus;

    String masterUsername;

    @Valid
    Set<ReceiptItemUpdateRequest> receiptItems;

    @Valid
    ReceiptDeliveryUpdateRequest receiptDelivery;

    @NotBlank
    String priceCurrency;

    @NotNull
    String note;

}
