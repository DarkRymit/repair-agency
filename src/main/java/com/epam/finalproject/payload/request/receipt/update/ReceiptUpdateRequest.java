package com.epam.finalproject.payload.request.receipt.update;

import com.epam.finalproject.model.entity.ReceiptStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class ReceiptUpdateRequest {

    @JsonIgnore
    Long id;

    ReceiptStatusEnum receiptStatus;

    Long masterId;

    Set<ReceiptItemUpdateRequest> receiptItems;

    ReceiptDeliveryUpdateRequest receiptDelivery;

    String note;

}
