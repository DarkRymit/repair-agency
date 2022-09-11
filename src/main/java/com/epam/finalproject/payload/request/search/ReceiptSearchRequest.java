package com.epam.finalproject.payload.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@AllArgsConstructor
public class ReceiptSearchRequest {
    String sort;


    Set<String> status;

    String customer;

    String master;
    @PositiveOrZero
    Integer page;
    @Positive
    Integer count;
}
