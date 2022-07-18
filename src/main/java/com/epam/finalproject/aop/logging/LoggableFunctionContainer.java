package com.epam.finalproject.aop.logging;

import org.slf4j.event.Level;

import java.util.EnumMap;
import java.util.Map;


public class LoggableFunctionContainer {
    private static final Map<Level, LogThrowConsumer> throwMap;
    private static final Map<Level, LogArgsConsumer> argsMap;
    private static final Map<Level, LogReturnConsumer> returnMap;

    private LoggableFunctionContainer() {
    }

    static {
        Map<Level, LogThrowConsumer> throwHandlerMapModifiable = new EnumMap<>(Level.class);
        throwHandlerMapModifiable.put(Level.ERROR, (logger, format, signature, throwable) -> logger.error(format, signature.getName(), throwable));
        throwHandlerMapModifiable.put(Level.WARN, (logger, format, signature, throwable) -> logger.warn(format, signature.getName(), throwable));
        throwHandlerMapModifiable.put(Level.INFO, (logger, format, signature, throwable) -> logger.info(format, signature.getName(), throwable));
        throwHandlerMapModifiable.put(Level.DEBUG, (logger, format, signature, throwable) -> logger.debug(format, signature.getName(), throwable));
        throwHandlerMapModifiable.put(Level.TRACE, (logger, format, signature, throwable) -> logger.trace(format, signature.getName(), throwable));
        throwMap = Map.copyOf(throwHandlerMapModifiable);

        Map<Level, LogArgsConsumer> argsHandlerMapModifiable = new EnumMap<>(Level.class);
        argsHandlerMapModifiable.put(Level.ERROR, (logger, format, signature, args) -> logger.error(format, signature.getName(), args));
        argsHandlerMapModifiable.put(Level.WARN, (logger, format, signature, args) -> logger.warn(format, signature.getName(), args));
        argsHandlerMapModifiable.put(Level.INFO, (logger, format, signature, args) -> logger.info(format, signature.getName(), args));
        argsHandlerMapModifiable.put(Level.DEBUG, (logger, format, signature, args) -> logger.debug(format, signature.getName(), args));
        argsHandlerMapModifiable.put(Level.TRACE, (logger, format, signature, args) -> logger.trace(format, signature.getName(), args));
        argsMap = Map.copyOf(argsHandlerMapModifiable);

        Map<Level, LogReturnConsumer> returnHandlerMapModifiable = new EnumMap<>(Level.class);
        returnHandlerMapModifiable.put(Level.ERROR, (logger, format, signature, result) -> logger.error(format, signature.getName(), result));
        returnHandlerMapModifiable.put(Level.WARN, (logger, format, signature, result) -> logger.warn(format, signature.getName(), result));
        returnHandlerMapModifiable.put(Level.INFO, (logger, format, signature, result) -> logger.info(format, signature.getName(), result));
        returnHandlerMapModifiable.put(Level.DEBUG, (logger, format, signature, result) -> logger.debug(format, signature.getName(), result));
        returnHandlerMapModifiable.put(Level.TRACE, (logger, format, signature, result) -> logger.trace(format, signature.getName(), result));
        returnMap = Map.copyOf(returnHandlerMapModifiable);

    }

    public static Map<Level, LogThrowConsumer> throwMap(){
        return throwMap;
    }
    public static Map<Level, LogArgsConsumer> argsMap(){
        return argsMap;
    }
    public static Map<Level, LogReturnConsumer> returnMap(){
        return returnMap;
    }

}
