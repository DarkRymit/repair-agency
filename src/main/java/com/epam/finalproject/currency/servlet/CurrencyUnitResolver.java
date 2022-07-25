package com.epam.finalproject.currency.servlet;

import org.springframework.lang.Nullable;

import javax.money.CurrencyUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CurrencyUnitResolver {
    CurrencyUnit resolveCurrencyUnit(HttpServletRequest request);

    void setCurrencyUnit(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable CurrencyUnit currencyUnit);
}
