package com.epam.finalproject.currency.context;

import org.springframework.lang.Nullable;

import javax.money.CurrencyUnit;

public interface CurrencyUnitContext {
    @Nullable
    CurrencyUnit getCurrencyUnit();
}
