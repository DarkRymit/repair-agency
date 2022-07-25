package com.epam.finalproject.currency.servlet;

import javax.money.CurrencyUnit;

public abstract class AbstractCurrencyUnitResolver implements CurrencyUnitResolver {
    private CurrencyUnit defaultCurrencyUnit;

    public CurrencyUnit getDefaultCurrencyUnit() {
        return defaultCurrencyUnit;
    }

    public void setDefaultCurrencyUnit(CurrencyUnit defaultCurrencyUnit) {
        this.defaultCurrencyUnit = defaultCurrencyUnit;
    }

}

