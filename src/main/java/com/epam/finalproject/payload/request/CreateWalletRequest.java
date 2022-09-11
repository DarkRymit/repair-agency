package com.epam.finalproject.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletRequest {

    @NotBlank
    @Size(min = 4,max = 20)
    private String name;

    @NotBlank
    @Size(min = 3,max = 3)
    private String currency;


}
