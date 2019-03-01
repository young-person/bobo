
package com.app.distributed;

import com.app.distributed.filter.TransactionInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public abstract class AbstractTransactionAspect {

    private TransactionInterceptor transactionInterceptor;

    protected void setTransactionInterceptor(final TransactionInterceptor transactionInterceptor) {
        this.transactionInterceptor = transactionInterceptor;
    }

    @Pointcut("@annotation(com.app.duddo.service.impl.annotation.Myth)")
    public void transactionInterceptor() {}

    @Around("transactionInterceptor()")
    public Object interceptMythAnnotationMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return transactionInterceptor.interceptor(proceedingJoinPoint);
    }

    public abstract int getOrder();
}
