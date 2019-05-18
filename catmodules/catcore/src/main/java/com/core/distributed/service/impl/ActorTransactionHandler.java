package com.app.distributed.service.impl;

import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.context.TransactionContextLocal;
import com.app.distributed.service.TransactionHandler;
import com.app.distributed.transaction.CatTransactionEngine;
import com.bobo.enums.JTAEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActorTransactionHandler implements TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ActorTransactionHandler.class);

    private final CatTransactionEngine transactionEngine;

    public ActorTransactionHandler(final CatTransactionEngine transactionEngine) {
        this.transactionEngine = transactionEngine;
    }

    @Override
    public Object handler(final ProceedingJoinPoint point, final TransactionContextBean transactionContext) throws Throwable {
        try {
            //先保存事务日志
            transactionEngine.actorTransaction(point, transactionContext);
            //发起调用 执行try方法
            final Object proceed = point.proceed();
            //执行成功 更新状态为commit
            transactionEngine.updateStatus(JTAEnum.COMMIT.getCode());
            return proceed;
        } catch (Throwable throwable) {
            logger.info("执行分布式事务接口失败,事务id：{}",transactionContext.getTransId());
            transactionEngine.failTransaction(throwable.getMessage());
            throw throwable;
        } finally {
            TransactionContextLocal.getInstance().remove();
        }
    }
}