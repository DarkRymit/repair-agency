package com.epam.finalproject.config;

import com.epam.finalproject.currency.servlet.CurrencyUnitChangeInterceptor;
import com.epam.finalproject.currency.servlet.CurrencyUnitContextPutInHolderInterceptor;
import com.epam.finalproject.currency.servlet.CurrencyUnitContextResolver;
import com.epam.finalproject.currency.servlet.SessionCurrencyUnitResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.money.Monetary;
import java.util.Locale;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("uk-UA"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public CurrencyUnitContextResolver currencyUnitContextResolver(){
        SessionCurrencyUnitResolver resolver = new  SessionCurrencyUnitResolver();
        resolver.setDefaultCurrencyUnit(Monetary.getCurrency("USD"));
        return resolver;
    }
    @Bean
    public CurrencyUnitChangeInterceptor currencyUnitChangeInterceptor() {
        CurrencyUnitChangeInterceptor interceptor = new CurrencyUnitChangeInterceptor();
        interceptor.setCurrencyUnitResolver(currencyUnitContextResolver());
        return interceptor;
    }
    @Bean
    public CurrencyUnitContextPutInHolderInterceptor currencyUnitContextPutInHolderInterceptor() {
        CurrencyUnitContextPutInHolderInterceptor interceptor = new CurrencyUnitContextPutInHolderInterceptor();
        interceptor.setCurrencyUnitContextResolver(currencyUnitContextResolver());
        return interceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(currencyUnitChangeInterceptor());
        registry.addInterceptor(currencyUnitContextPutInHolderInterceptor());
    }
}
