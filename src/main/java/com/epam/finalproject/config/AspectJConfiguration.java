package com.epam.finalproject.config;

import com.epam.finalproject.aop.logging.LoggableAspect;
import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class AspectJConfiguration {
    @Bean
    public LoggableAspect loggableAspect() {
        return Aspects.aspectOf(LoggableAspect.class);
    }
}
