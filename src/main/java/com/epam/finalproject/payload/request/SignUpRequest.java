package com.epam.finalproject.payload.request;


import com.epam.finalproject.validators.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 8, max = 14)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 14)
    private String password;

    @NotBlank
    @Size(min = 1, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 20)
    private String lastName;

    @NotBlank
    @PhoneNumber
    private String phone;
}
