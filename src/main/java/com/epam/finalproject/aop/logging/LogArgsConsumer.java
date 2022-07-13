package com.epam.finalproject.aop.logging;

import org.aspectj.lang.Signature;
import org.slf4j.Logger;

@FunctionalInterface
interface LogArgsConsumer {
    void handle(Logger logger, String format, Signature signature, Object[] args);
}
