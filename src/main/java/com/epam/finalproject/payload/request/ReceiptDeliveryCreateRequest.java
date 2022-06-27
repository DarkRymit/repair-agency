package com.epam.finalproject.payload.request;

import lombok.Data;

@Data
public class ReceiptDeliveryCreateRequest {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;
}
