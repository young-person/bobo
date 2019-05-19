package com.core.distributed.service;

import com.core.distributed.context.TransactionContextBean;

public interface TransactionFactoryService<T> {
    public Class<T> factoryOf(TransactionContextBean contextBean) throws Throwable;
}
