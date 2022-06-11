package com.epam.finalproject.controller;


import lombok.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Size(min = 2, max = 40)
    private String firstName;

    @Size(min = 2, max = 40)
    private String lastName;

    @Size(min = 6, max = 40)
    private String phone;
}
