package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.SimpleCurrencyUnitContext;

import javax.money.CurrencyUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractCurrencyUnitContextResolver extends AbstractCurrencyUnitResolver implements CurrencyUnitContextResolver {


    @Override
    public CurrencyUnit resolveCurrencyUnit(HttpServletRequest request) {
        return resolveCurrencyUnitContext(request).getCurrencyUnit();
    }

    @Override
    public void setCurrencyUnit(HttpServletRequest request, HttpServletResponse response, CurrencyUnit currencyUnit) {
        setCurrencyUnitContext(request, response, currencyUnit != null ? new SimpleCurrencyUnitContext(currencyUnit) : null);
    }

}

