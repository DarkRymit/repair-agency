package com.epam.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairCategoryDTO {

    private Long id;

    private String keyName;

    private String name;

    private AppLocaleDTO language;

}
