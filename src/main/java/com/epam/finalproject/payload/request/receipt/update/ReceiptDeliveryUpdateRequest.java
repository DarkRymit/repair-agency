package com.epam.finalproject.payload.request.receipt.update;

import lombok.Data;

@Data
public class ReceiptDeliveryUpdateRequest {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;
}
