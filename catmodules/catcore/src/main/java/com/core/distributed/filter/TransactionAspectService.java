package com.app.distributed.filter;

import com.app.distributed.context.TransactionContextBean;
import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionAspectService {
    Object invoke(TransactionContextBean transactionContext, ProceedingJoinPoint point) throws Throwable;
}
