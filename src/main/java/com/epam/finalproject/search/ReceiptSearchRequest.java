package com.epam.finalproject.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ReceiptSearchRequest {
    String sort;
    Set<String> status;
    String user;
    String master;
    Integer page;
    Integer count;
}
