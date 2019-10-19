package com.core.distributed.filter;

import org.aspectj.lang.ProceedingJoinPoint;

import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.service.TransactionFactoryService;
import com.core.distributed.service.TransactionHandler;
import com.core.utils.SpringContextUtil;

public class TransactionAspectServiceImpl implements TransactionAspectService {
    private final TransactionFactoryService transactionFactoryService;
    public TransactionAspectServiceImpl(final TransactionFactoryService transactionFactoryService) {
        this.transactionFactoryService = transactionFactoryService;
    }
    @Override
    public Object invoke(TransactionContextBean transactionContext, ProceedingJoinPoint point) throws Throwable {
        final Class clazz = transactionFactoryService.factoryOf(transactionContext);
        final TransactionHandler transactionHandler = (TransactionHandler) SpringContextUtil.getBean(clazz);
        return transactionHandler.handler(point, transactionContext);
    }
}
