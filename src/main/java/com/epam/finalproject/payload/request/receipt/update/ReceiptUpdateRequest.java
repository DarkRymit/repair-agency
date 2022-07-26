package com.epam.finalproject.payload.request.receipt.update;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptUpdateRequest {

    @JsonIgnore
    Long id;

    ReceiptStatusEnum receiptStatus;

    String masterUsername;

    Set<ReceiptItemUpdateRequest> receiptItems;

    ReceiptDeliveryUpdateRequest receiptDelivery;

    String priceCurrency;

    String note;

}
