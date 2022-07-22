package com.epam.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptStatusFlowDTO {

    private Long id;

    ReceiptStatusDTO fromStatus;

    ReceiptStatusDTO toStatus;

    RoleDTO role;

}
