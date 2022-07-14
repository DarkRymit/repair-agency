package com.epam.finalproject.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Slf4j
public class LoggableAspect {

    public static final String METHOD_CALLED_WITH_ARGS = "method {} called with args {}";
    public static final String METHOD_RETURN = "method {} return {}";
    public static final String METHOD_THROW = "method {} throw ";

    @Pointcut("@annotation(loggable)")
    public void callAt(Loggable loggable) {
        // Pointcuts need to have empty body
    }

    @Around(value = "callAt(annotation) && within(com.epam.finalproject..*) && execution(* com.epam.finalproject..*(..))", argNames = "pjp,annotation")
    public Object around(ProceedingJoinPoint pjp, Loggable annotation) throws Throwable {
        Signature signature = pjp.getSignature();

        Logger logger = getLogger(annotation, signature);

        if (!annotation.skipArgs()) {
            logArgs(pjp, annotation, signature, logger);
        }

        try {
            Object result = pjp.proceed();

            if (!annotation.skipReturn()) {
                logReturn(annotation, signature, logger, result);
            }

            return result;
        } catch (Throwable throwable) {

            boolean skipThrow = annotation.skipThrow();

            if(!skipThrow && Arrays.asList(annotation.ignore()).contains(throwable.getClass())){
                skipThrow = true;
            }

            if (!skipThrow) {
                logThrow(annotation, signature, logger, throwable);
            }

            throw throwable;
        }
    }

    private Logger getLogger(Loggable annotation, Signature signature) {
        Logger logger;
        if (annotation.log().isBlank()) {
            logger = LoggerFactory.getLogger(signature.getDeclaringType());
        } else {
            logger = LoggerFactory.getLogger(annotation.log());
        }
        return logger;
    }

    private void logThrow(Loggable annotation, Signature signature, Logger logger, Throwable throwable) {
        LoggableFunctionContainer.throwMap().get(annotation.level()).handle(logger, METHOD_THROW, signature, throwable);
    }

    private void logReturn(Loggable annotation, Signature signature, Logger logger, Object result) {
        LoggableFunctionContainer.returnMap().get(annotation.level()).handle(logger, METHOD_RETURN, signature, result);
    }

    private void logArgs(ProceedingJoinPoint pjp, Loggable annotation, Signature signature, Logger logger) {
        LoggableFunctionContainer.argsMap().get(annotation.level()).handle(logger, METHOD_CALLED_WITH_ARGS, signature, pjp.getArgs());
    }
}
