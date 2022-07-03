package com.epam.finalproject.payload.request.receipt.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDeliveryUpdateRequest {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;
}
