package com.epam.finalproject.payload.request.receipt.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDeliveryCreateRequest {


    @NotBlank
    private String country;

    private String state;

    @NotBlank
    private String city;

    @NotBlank
    private String localAddress;

    private String postalCode;
}
