package com.epam.finalproject.config;


import com.epam.finalproject.repository.RepairCategoryRepository;
import com.epam.finalproject.repository.RepairWorkRepository;
import lombok.Builder;
import lombok.Value;

import javax.money.CurrencyUnit;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Supplier;

@Value
@Builder
public class ModelMapperParameters {
    Supplier<Locale> localeSupplier;
    Supplier<TimeZone> timeZoneSupplier;
    Supplier<CurrencyUnit> currencyUnitSupplier;
    RepairWorkRepository repairWorkRepository;
    RepairCategoryRepository repairCategoryRepository;
}
