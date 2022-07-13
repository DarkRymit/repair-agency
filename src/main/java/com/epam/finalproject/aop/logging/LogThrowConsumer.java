package com.epam.finalproject.aop.logging;

import org.aspectj.lang.Signature;
import org.slf4j.Logger;

interface LogThrowConsumer {
    void handle(Logger logger, String format, Signature signature, Throwable throwable);
}
