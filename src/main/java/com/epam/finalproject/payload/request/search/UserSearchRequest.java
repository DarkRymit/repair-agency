package com.epam.finalproject.payload.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserSearchRequest {
    String sort;

    Set<String> status;

    String username;

    @PositiveOrZero
    Integer page;

    @Positive
    Integer count;
}
