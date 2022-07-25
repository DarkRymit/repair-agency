package com.epam.finalproject.currency.servlet;

import com.epam.finalproject.currency.context.CurrencyUnitContext;
import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor
@Slf4j
public class CurrencyUnitContextPutInHolderInterceptor implements HandlerInterceptor {

    private CurrencyUnitContextResolver currencyUnitContextResolver;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        CurrencyUnitContext context = currencyUnitContextResolver.resolveCurrencyUnitContext(request);
        CurrencyUnitContextHolder.setCurrencyUnitContext(context);
        return true;
    }

    public CurrencyUnitContextResolver getCurrencyUnitContextResolver() {
        return currencyUnitContextResolver;
    }

    public void setCurrencyUnitContextResolver(CurrencyUnitContextResolver currencyUnitContextResolver) {
        this.currencyUnitContextResolver = currencyUnitContextResolver;
    }

}


