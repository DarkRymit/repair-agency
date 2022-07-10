package com.epam.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDeliveryDTO {

    private String country;

    private String state;

    private String city;

    private String localAddress;

    private String postalCode;

}
