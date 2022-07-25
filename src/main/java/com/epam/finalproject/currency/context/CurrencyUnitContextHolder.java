package com.epam.finalproject.currency.context;

import org.springframework.lang.Nullable;

import javax.money.CurrencyUnit;
import java.util.Objects;

public class CurrencyUnitContextHolder {
    private static final ThreadLocal<CurrencyUnitContext> currencyUnitContextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<CurrencyUnitContext> inheritableCurrencyUnitContextThreadLocal = new ThreadLocal<>();

    private CurrencyUnitContextHolder() {
    }

    public static void resetCurrencyUnitContext() {
        currencyUnitContextThreadLocal.remove();
        inheritableCurrencyUnitContextThreadLocal.remove();
    }
    public static void setCurrencyUnitContext(@Nullable CurrencyUnitContext currencyUnitContext, boolean inheritable) {
        if (currencyUnitContext == null) {
            resetCurrencyUnitContext();
        } else if (inheritable) {
            inheritableCurrencyUnitContextThreadLocal.set(currencyUnitContext);
            currencyUnitContextThreadLocal.remove();
        } else {
            currencyUnitContextThreadLocal.set(currencyUnitContext);
            inheritableCurrencyUnitContextThreadLocal.remove();
        }
    }
    @Nullable
    public static CurrencyUnitContext getCurrencyUnitContext() {
        CurrencyUnitContext currencyUnitContext = currencyUnitContextThreadLocal.get();
        if (currencyUnitContext == null) {
            currencyUnitContext = inheritableCurrencyUnitContextThreadLocal.get();
        }
        return currencyUnitContext;
    }

    public static void setCurrencyUnitContext(@Nullable CurrencyUnitContext currencyUnitContext) {
        setCurrencyUnitContext(currencyUnitContext, false);
    }

    public static CurrencyUnit getCurrencyUnit() {
        return getCurrencyUnit(Objects.requireNonNull(getCurrencyUnitContext()));
    }

    public static CurrencyUnit getCurrencyUnit(CurrencyUnitContext currencyUnitContext) {
        return currencyUnitContext.getCurrencyUnit();
    }
}
