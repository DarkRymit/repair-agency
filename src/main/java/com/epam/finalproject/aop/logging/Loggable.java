package com.epam.finalproject.aop.logging;

import org.slf4j.event.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {

    Level level() default Level.DEBUG;

    String log() default "";

    boolean skipArgs() default false;

    boolean skipReturn() default false;

    boolean skipThrow() default true;

    Class<? extends Throwable>[] ignore() default {};

}
