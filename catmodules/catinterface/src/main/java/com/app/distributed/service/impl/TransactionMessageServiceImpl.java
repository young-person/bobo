package com.app.distributed.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.app.distributed.CatMessageEntity;
import com.app.distributed.CatParticipant;
import com.app.distributed.CatTransaction;
import com.app.distributed.disruptor.CatTransactionEventPublisher;
import com.app.distributed.service.MqTransactionMessageService;
import com.app.distributed.service.TransactionMessageService;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.serializer.impl.CJavaSerializer;

import java.util.List;
import java.util.Objects;

public class TransactionMessageServiceImpl implements TransactionMessageService {
    private volatile CObjectSerializer serializer;

    private volatile MqTransactionMessageService mqTransactionMessageService;
    private CatTransactionEventPublisher publisher;

    public TransactionMessageServiceImpl(CatTransactionEventPublisher publisher){
        this.publisher = publisher;
    }

    @Override
    public boolean sendMessage(CatTransaction transaction) {
        if (Objects.isNull(transaction)) {
            return false;
        }
        final List<CatParticipant> mythParticipants = transaction.getParticipants();
        /*
         * 这里的这个判断很重要，不为空，表示本地的方法执行成功，需要执行远端的rpc方法
         * 为什么呢，因为我会在切面的finally里面发送消息，意思是切面无论如何都需要发送mq消息
         * 那么考虑问题，如果本地执行成功，调用rpc的时候才需要发
         * 如果本地异常，则不需要发送mq ，此时mythParticipants为空
         */
        if (CollectionUtils.isNotEmpty(mythParticipants)) {
            for (CatParticipant participant : mythParticipants) {
                CatMessageEntity messageEntity = new CatMessageEntity(participant.getTransId(), participant.getInvocation());
                try {
                    final byte[] message = getObjectSerializer().serialize(messageEntity);
                    getMythMqSendService().sendMessage(participant.getDestination(), participant.getPattern(), message);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Boolean.FALSE;
                }
            }
            //这里为什么要这么做呢？ 主要是为了防止在极端情况下，发起者执行过程中，突然自身down 机
            //造成消息未发送，新增一个状态标记，如果出现这种情况，通过定时任务发送消息
            transaction.setStatus(JTAEnum.COMMIT.getCode());
            publisher.publishEvent(transaction, JTAEnum.UPDATE_STATUS.getCode());
        }
        return Boolean.TRUE;
    }
    private synchronized MqTransactionMessageService getMythMqSendService() {
        if (mqTransactionMessageService == null) {
            synchronized (TransactionMessageServiceImpl.class) {
                if (mqTransactionMessageService == null) {
                    //mqTransactionMessageService = new TransactionMessageServiceImpl(publisher);
                }
            }
        }
        return mqTransactionMessageService;
    }

    private synchronized CObjectSerializer getObjectSerializer() {
        if (serializer == null) {
            synchronized (TransactionMessageServiceImpl.class) {
                if (serializer == null) {
                    serializer = new CJavaSerializer();
                }
            }
        }
        return serializer;
    }
}
