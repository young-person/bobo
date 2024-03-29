package com.core.distributed.service.impl;

import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import com.core.distributed.service.TransactionHandler;
import com.core.distributed.transaction.CatTransactionEngine;
import com.bobo.base.CatException;
import org.aspectj.lang.ProceedingJoinPoint;

public class StartTransactionHandler implements TransactionHandler {

    private final CatTransactionEngine transactionEngine;

    public StartTransactionHandler(final CatTransactionEngine transactionEngine) {
        this.transactionEngine = transactionEngine;
    }

    @Override
    public Object handler(ProceedingJoinPoint point, TransactionContextBean transactionContext) throws Throwable {
        try {
            transactionEngine.begin(point);
            return point.proceed();
        } catch (CatException throwable) {
            //更新失败的日志信息
            transactionEngine.failTransaction(throwable.getMessage());
            throw  throwable;
        } finally {
            //发送消息
            transactionEngine.sendMessage();
            transactionEngine.cleanThreadLocal();
            TransactionContextLocal.getInstance().remove();
        }
    }
}
