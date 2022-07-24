package com.epam.finalproject.config;


import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.model.entity.RepairWork;
import lombok.Builder;
import lombok.Value;
import org.modelmapper.Converter;

import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Supplier;

@Value
@Builder
public class ModelMapperParameters {
    Supplier<Locale> localeSupplier;
    Supplier<TimeZone> timeZoneSupplier;
    Converter<RepairWork, RepairWorkDTO> repairWorkRepairWorkDTOPostConverter;
    Converter<RepairCategory, RepairCategoryDTO> repairCategoryRepairCategoryDTOPostConverter;
}
