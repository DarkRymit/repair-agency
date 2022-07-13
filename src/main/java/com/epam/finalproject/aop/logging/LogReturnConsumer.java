package com.epam.finalproject.aop.logging;

import org.aspectj.lang.Signature;
import org.slf4j.Logger;

interface LogReturnConsumer {
    void handle(Logger logger, String format, Signature signature, Object result);
}