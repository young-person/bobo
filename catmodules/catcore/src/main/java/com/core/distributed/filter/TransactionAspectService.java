package com.core.distributed.filter;

import com.core.distributed.context.TransactionContextBean;
import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionAspectService {
    Object invoke(TransactionContextBean transactionContext, ProceedingJoinPoint point) throws Throwable;
}
