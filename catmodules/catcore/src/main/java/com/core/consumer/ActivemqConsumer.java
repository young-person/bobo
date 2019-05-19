package com.core.consumer;

import com.core.distributed.service.mq.ReceiveMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;


@ConditionalOnProperty(prefix = "spring.activemq", name = "broker-url")
public class ActivemqConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ActivemqConsumer.class);


    private final ReceiveMessageService receiveService;

    @Autowired
    public ActivemqConsumer(ReceiveMessageService receiveService) {
        this.receiveService = receiveService;
    }


    @JmsListener(destination = "account",containerFactory = "queueListenerContainerFactory")
    public void receiveQueue(byte[] message) {
        logger.info("=========扣减账户信息接收到Myth框架传入的信息==========");
        final Boolean success = receiveService.processMessage(message);
        if(success){
            //消费成功，消息出队列，否则不消费
        }
    }
}
