package com.epam.finalproject.payload.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class MasterSearchRequest {
    String sort;
    String username;
    @PositiveOrZero
    Integer page;
    @Positive
    Integer count;
}
