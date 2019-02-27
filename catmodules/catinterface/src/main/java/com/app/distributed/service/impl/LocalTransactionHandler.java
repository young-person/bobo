package com.app.distributed.service.impl;

import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.service.TransactionHandler;
import org.aspectj.lang.ProceedingJoinPoint;

public class LocalTransactionHandler implements TransactionHandler {
    @Override
    public Object handler(ProceedingJoinPoint point, TransactionContextBean transactionContext) throws Throwable {
        return point.proceed();
    }
}
