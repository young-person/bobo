package com.core.distributed.service.impl;

import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.service.TransactionHandler;
import org.aspectj.lang.ProceedingJoinPoint;

public class LocalTransactionHandler implements TransactionHandler {
    @Override
    public Object handler(ProceedingJoinPoint point, TransactionContextBean transactionContext) throws Throwable {
        return point.proceed();
    }
}
