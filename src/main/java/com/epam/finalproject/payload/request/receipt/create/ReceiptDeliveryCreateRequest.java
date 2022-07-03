package com.epam.finalproject.payload.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDeliveryCreateRequest {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;
}
