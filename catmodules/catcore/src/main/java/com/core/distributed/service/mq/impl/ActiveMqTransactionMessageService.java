package com.core.distributed.service.mq.impl;

import com.core.distributed.service.mq.MqTransactionMessageService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.Objects;

import static com.bobo.enums.MessageTypeEnum.P2P;
import static com.bobo.enums.MessageTypeEnum.TOPIC;

public class ActiveMqTransactionMessageService implements MqTransactionMessageService {
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    @Override
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        Destination queue = new ActiveMQQueue(destination);
        if (Objects.equals(P2P.getCode(), pattern)) {
            queue = new ActiveMQQueue(destination);
        } else if (Objects.equals(TOPIC.getCode(), pattern)) {
            queue = new ActiveMQTopic(destination);
        }
        jmsTemplate.convertAndSend(queue, message);
    }
}
