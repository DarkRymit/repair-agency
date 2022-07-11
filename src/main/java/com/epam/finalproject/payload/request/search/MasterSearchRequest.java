package com.epam.finalproject.payload.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MasterSearchRequest {
    String sort;
    String username;
    Integer page;
    Integer count;
}
