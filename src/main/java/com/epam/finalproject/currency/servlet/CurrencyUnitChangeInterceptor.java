package com.epam.finalproject.currency.servlet;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor
@Slf4j
@Order(100)
public class CurrencyUnitChangeInterceptor implements HandlerInterceptor {
    public static final String DEFAULT_PARAM_NAME = "currency";
    private String paramName = DEFAULT_PARAM_NAME;

    private CurrencyUnitResolver currencyUnitResolver;

    private boolean ignoreInvalidCurrency = false;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        String newCurrencyUnit = request.getParameter(paramName);
        if (newCurrencyUnit != null) {
            try {
                currencyUnitResolver.setCurrencyUnit(request, response, parseCurrencyUnitValue(newCurrencyUnit));
            } catch (IllegalArgumentException e) {
                if (!isIgnoreInvalidCurrency()) {
                    throw e;
                }
                log.debug("Ignoring invalid currency value [" + newCurrencyUnit + "]: " + e.getMessage());
            }
        }
        return true;
    }

    @Nullable
    protected CurrencyUnit parseCurrencyUnitValue(String currencyValue) {
        return Monetary.getCurrency(currencyValue);
    }

    public CurrencyUnitResolver getCurrencyUnitResolver() {
        return currencyUnitResolver;
    }

    public void setCurrencyUnitResolver(CurrencyUnitResolver currencyUnitResolver) {
        this.currencyUnitResolver = currencyUnitResolver;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public boolean isIgnoreInvalidCurrency() {
        return ignoreInvalidCurrency;
    }

    public void setIgnoreInvalidCurrency(boolean ignoreInvalidCurrency) {
        this.ignoreInvalidCurrency = ignoreInvalidCurrency;
    }
}


