package com.epam.finalproject.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponseCreateRequest {

    private Long receiptId;

    @NotNull
    @Size(max = 255)
    private String text;

    @NotNull
    @Max(5)
    @Min(1)
    private Integer rating;
}
