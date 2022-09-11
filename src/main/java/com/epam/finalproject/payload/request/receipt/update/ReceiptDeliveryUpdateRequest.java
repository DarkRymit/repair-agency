package com.epam.finalproject.payload.request.receipt.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDeliveryUpdateRequest {

    @NotBlank
    private String country;

    private String state;

    @NotNull
    private String city;

    @NotBlank
    private String localAddress;

    private String postalCode;
}
