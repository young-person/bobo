package com.app.distributed.service.impl;

import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.service.TransactionFactoryService;
import com.app.distributed.transaction.CatTransactionEngine;
import com.bobo.enums.JTAEnum;

import java.util.Objects;

/**
 * 事务工厂
 */
public class TransactionFactoryServiceImpl implements TransactionFactoryService {

    private final CatTransactionEngine engine;

    public TransactionFactoryServiceImpl(final CatTransactionEngine engine){
        this.engine = engine;
    }

    @Override
    public Class factoryBean(TransactionContextBean contextBean) throws Throwable{
        /**
         * 如果事务还没开启 那么应该进入发起调用
         */
        if (Objects.isNull(contextBean) || !engine.isBegin()){
            return TransactionFactoryServiceImpl.class;
        }else{
            if (contextBean.getRole() == JTAEnum.LOCAL.getCode()) {
                return LocalTransactionHandler.class;
            }
            return ActorTransactionHandler.class;
        }
    }
}
