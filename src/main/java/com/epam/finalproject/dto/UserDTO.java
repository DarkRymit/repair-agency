package com.epam.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<WalletDTO> wallets = new HashSet<>();

    private ZonedDateTime creationDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

}
