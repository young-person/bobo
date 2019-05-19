package com.core.distributed.service;

import com.core.distributed.context.TransactionContextBean;
import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionHandler {
    Object handler(ProceedingJoinPoint point, TransactionContextBean transactionContext) throws Throwable;
}
