package com.core.distributed.service.mq.impl;

import java.util.List;
import java.util.Objects;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.serializer.impl.CJavaSerializer;
import com.core.distributed.CatMessageEntity;
import com.core.distributed.CatParticipant;
import com.core.distributed.CatTransaction;
import com.core.distributed.disruptor.CatTransactionEventPublisher;
import com.core.distributed.service.mq.MqTransactionMessageService;
import com.core.distributed.service.mq.SendMessageService;
import com.core.utils.SpringContextUtil;

public class SendMessageServiceImpl implements SendMessageService {
    private volatile CObjectSerializer serializer;

    private volatile MqTransactionMessageService mqTransactionMessageService;


    private CatTransactionEventPublisher publisher;

    public SendMessageServiceImpl(CatTransactionEventPublisher publisher){
        this.publisher = publisher;
    }

    @Override
    public boolean sendMessage(CatTransaction transaction) {
        if (Objects.isNull(transaction)) {
            return false;
        }
        final List<CatParticipant> participants = transaction.getParticipants();
        /*
         * 这里的这个判断很重要，不为空，表示本地的方法执行成功，需要执行远端的rpc方法
         * 为什么呢，因为我会在切面的finally里面发送消息，意思是切面无论如何都需要发送mq消息
         * 那么考虑问题，如果本地执行成功，调用rpc的时候才需要发
         * 如果本地异常，则不需要发送mq ，此时participants为空
         */
        if (CollectionUtils.isNotEmpty(participants)) {
            for (CatParticipant participant : participants) {
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
            synchronized (SendMessageServiceImpl.class) {
                if (mqTransactionMessageService == null) {
                    mqTransactionMessageService = SpringContextUtil.getBean(MqTransactionMessageService.class);
                }
            }
        }
        return mqTransactionMessageService;
    }

    private synchronized CObjectSerializer getObjectSerializer() {
        if (serializer == null) {
            synchronized (SendMessageServiceImpl.class) {
                if (serializer == null) {
                    serializer = new CJavaSerializer();
                }
            }
        }
        return serializer;
    }
}
