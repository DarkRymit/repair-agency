package com.epam.finalproject.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserSearchRequest {
    String sort;
    Set<String> status;
    String username;
    Integer page;
    Integer count;
}
