package com.epam.finalproject.payload.request.receipt.pay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptPayRequest {

    @JsonIgnore
    Long id;

    Long walletId;

}
