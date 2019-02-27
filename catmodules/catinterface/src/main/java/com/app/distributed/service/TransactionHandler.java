package com.app.distributed.service;

import com.app.distributed.context.TransactionContextBean;
import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionHandler {
    Object handler(ProceedingJoinPoint point, TransactionContextBean transactionContext) throws Throwable;
}
