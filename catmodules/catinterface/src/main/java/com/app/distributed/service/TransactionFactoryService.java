package com.app.distributed.service;

import com.app.distributed.context.TransactionContextBean;

public interface TransactionFactoryService<T> {
    public Class<T> factoryOf(TransactionContextBean contextBean) throws Throwable;
}