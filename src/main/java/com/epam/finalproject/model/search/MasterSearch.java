package com.epam.finalproject.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;

@Value
@AllArgsConstructor
@Builder
public class MasterSearch {
    PageRequest pageRequest;
    String username;
}
