package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CurrencyUnitContextResolver extends CurrencyUnitResolver {
    CurrencyUnitContext resolveCurrencyUnitContext(HttpServletRequest request);
    void setCurrencyUnitContext(HttpServletRequest request, @Nullable HttpServletResponse response,CurrencyUnitContext context);
}
