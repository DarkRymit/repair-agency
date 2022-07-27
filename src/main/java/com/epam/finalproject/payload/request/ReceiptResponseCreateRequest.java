package com.epam.finalproject.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponseCreateRequest {

    private Long receiptId;

    private String text;

    private Integer rating;

}
