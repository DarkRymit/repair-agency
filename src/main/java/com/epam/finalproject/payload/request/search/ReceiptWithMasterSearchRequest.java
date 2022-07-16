package com.epam.finalproject.payload.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ReceiptWithMasterSearchRequest {
    String sort;
    Set<String> status;
    String customer;
    Integer page;
    Integer count;
}
