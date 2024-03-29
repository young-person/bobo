package com.core.distributed.service.mq.impl;

import com.core.distributed.service.mq.MqTransactionMessageService;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMqTransactionMessageService implements MqTransactionMessageService {
    private KafkaTemplate kafkaTemplate;

    public void setKafkaTemplate(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendMessage(String destination, Integer pattern, byte[] message) {
        kafkaTemplate.send(destination, message);
    }
}
