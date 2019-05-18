package com.app.distributed.service.mq.impl;

import com.app.distributed.CatInvocation;
import com.app.distributed.CatMessageEntity;
import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.context.TransactionContextLocal;
import com.app.distributed.disruptor.CatTransactionEventPublisher;
import com.app.distributed.service.CoordinatorService;
import com.app.distributed.service.mq.ReceiveMessageService;
import com.bobo.base.CatException;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.SpringContextUtil;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReceiveMessageServiceImpl implements ReceiveMessageService {

    private static final Lock LOCK = new ReentrantLock();

    private volatile CObjectSerializer serializer;

    private CoordinatorService coordinatorService;

    private CatTransactionEventPublisher publisher;

    private CatConfig config;

    public ReceiveMessageServiceImpl(CoordinatorService coordinatorService, CatTransactionEventPublisher publisher, CatConfig config){
        this.coordinatorService = coordinatorService;
        this.publisher = publisher;
        this.config = config;
    }

    @Override
    public Boolean processMessage(byte[] message) {
        try {
            CatMessageEntity entity;
            try {
                entity = getObjectSerializer().deSerialize(message, CatMessageEntity.class);
            } catch (CatException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            /*
             * 1 检查该事务有没被处理过，已经处理过的 则不处理
             * 2 发起发射调用，调用接口，进行处理
             * 3 记录本地日志
             */
            LOCK.lock();
            final String transId = entity.getTransId();
            final CatTransaction mythTransaction = coordinatorService.findByTransationId(transId);
            //第一次调用 也就是服务down机，或者没有调用到的时候， 通过mq执行
            if (Objects.isNull(mythTransaction)) {
                try {
                    execute(entity);
                    //执行成功 保存成功的日志
                    final CatTransaction log = buildTransactionLog(transId, "",
                            JTAEnum.COMMIT.getCode(),
                            entity.getCatInvocation().getTargetClass().getName(),
                            entity.getCatInvocation().getMethod());
                    //submit(new CoordinatorAction(CoordinatorActionEnum.SAVE, log));
                    publisher.publishEvent(log, JTAEnum.SAVE.getCode());
                } catch (Exception e) {
                    //执行失败保存失败的日志
                    final CatTransaction log = buildTransactionLog(transId, e.getMessage(),
                            JTAEnum.FAILURE.getCode(),
                            entity.getCatInvocation().getTargetClass().getName(),
                            entity.getCatInvocation().getMethod());
                    publisher.publishEvent(log, JTAEnum.SAVE.getCode());
                    throw new RuntimeException(e);
                } finally {
                    TransactionContextLocal.getInstance().remove();
                }
            } else {
                //如果是执行失败的话
                if (mythTransaction.getStatus() == JTAEnum.FAILURE.getCode()) {
                    //如果超过了最大重试次数 则不执行
                    if (mythTransaction.getRetriedCount() >= config.getRetryMax()) {
                        logger.error("此事务已经超过了最大重试次数:{},执行接口为:{},方法为:{},事务id为：{}",new Object[]{config.getRetryMax(),entity.getCatInvocation().getTargetClass(),entity.getCatInvocation().getMethod(),entity.getTransId()});
                        return Boolean.FALSE;
                    }
                    try {
                        execute(entity);
                        //执行成功 更新日志为成功
                        mythTransaction.setStatus(JTAEnum.COMMIT.getCode());
                        publisher.publishEvent(mythTransaction, JTAEnum.UPDATE_STATUS.getCode());

                    } catch (Throwable e) {
                        //执行失败，设置失败原因和重试次数
                        mythTransaction.setErrorMsg(e.getCause().getMessage());
                        mythTransaction.setRetriedCount(mythTransaction.getRetriedCount() + 1);
                        publisher.publishEvent(mythTransaction, JTAEnum.UPDATE_FAIR.getCode());
                        throw new RuntimeException(e);
                    } finally {
                        TransactionContextLocal.getInstance().remove();
                    }
                }
            }

        } finally {
            LOCK.unlock();
        }
        return Boolean.TRUE;
    }



    private void execute(final CatMessageEntity entity) throws Exception {
        //设置事务上下文，这个类会传递给远端
        TransactionContextBean context = new TransactionContextBean();
        //设置事务id
        context.setTransId(entity.getTransId());
        //设置为发起者角色
        context.setRole(JTAEnum.LOCAL.getCode());
        TransactionContextLocal.getInstance().set(context);
        executeLocalTransaction(entity.getCatInvocation());
    }

    private void executeLocalTransaction(final CatInvocation invocation) throws Exception {
        if (Objects.nonNull(invocation)) {
            final Class clazz = invocation.getTargetClass();
            final String method = invocation.getMethod();
            final Object[] args = invocation.getArgs();
            final Class[] parameterTypes = invocation.getParameterTypes();
            final Object bean = SpringContextUtil.getBean(clazz);
            MethodUtils.invokeMethod(bean, method, args, parameterTypes);
            logger.info("执行本地协调事务:{}:{}",new Object[]{invocation.getTargetClass(),invocation.getMethod()});
        }
    }

    private CatTransaction buildTransactionLog(final String transId, final String errorMsg, final Integer status,
                                                final String targetClass, final String targetMethod) {
        CatTransaction logTransaction = new CatTransaction(transId);
        logTransaction.setRetriedCount(1);
        logTransaction.setStatus(status);
        logTransaction.setErrorMsg(errorMsg);
        logTransaction.setRole(JTAEnum.PROVIDER.getCode());
        logTransaction.setTargetClass(targetClass);
        logTransaction.setTargetMethod(targetMethod);
        return logTransaction;
    }

    private synchronized CObjectSerializer getObjectSerializer() {
        if (serializer == null) {
            synchronized (ReceiveMessageServiceImpl.class) {
                if (serializer == null) {
                    serializer = SpringContextUtil.getBean(CObjectSerializer.class);
                }
            }
        }
        return serializer;
    }
}
