package com.core.distributed.service.mq.impl;

import com.core.distributed.service.mq.MqTransactionMessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 基于rabbitMQ 分布式事物消息支持
 * rabbitMq消息稳定性最高 基本不丢数据
 */
public class RabbitMqTransactionMessageService implements MqTransactionMessageService, RabbitTemplate.ConfirmCallback {

    private AmqpTemplate amqpTemplate;
    public void setAmqpTemplate(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        amqpTemplate.convertAndSend(destination, message);
    }

    /**
     * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(final CorrelationData correlationData, final boolean ack, final String cause) {
        if (ack) {
            logger.info("rabbit mq 发送消息成功！");
        } else {
            logger.error("rabbit mq 发送消息失败:{}！",cause);
        }
    }
}
