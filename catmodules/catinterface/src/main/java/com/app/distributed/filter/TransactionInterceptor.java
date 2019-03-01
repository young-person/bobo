package com.app.distributed.filter;

import org.aspectj.lang.ProceedingJoinPoint;

public interface TransactionInterceptor {
    /**
     * tcc分布式事务拦截方法.
     *
     * @param pjp tcc切入点
     * @return Object
     * @throws Throwable 异常
     */
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable;
}
