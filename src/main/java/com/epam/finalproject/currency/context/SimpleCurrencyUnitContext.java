package com.epam.finalproject.currency.context;

import javax.money.CurrencyUnit;

public class SimpleCurrencyUnitContext implements CurrencyUnitContext{
    private final CurrencyUnit currencyUnit;

    public SimpleCurrencyUnitContext(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @Override
    public CurrencyUnit getCurrencyUnit() {
        return currencyUnit;
    }
}
