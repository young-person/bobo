package com.app.consumer;

import com.app.distributed.service.mq.ReceiveMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;


@ConditionalOnProperty(prefix = "spring.kafka.consumer", name = "bootstrap-servers")
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ReceiveMessageService receiveService;

    @Autowired
    public KafkaConsumer(ReceiveMessageService receiveService) {
        this.receiveService = receiveService;
    }

    @KafkaListener(topics = {"account"})
    public void kafkaListener(ConsumerRecord<?, byte[]> record) {
        Optional<?> messages = Optional.ofNullable(record.value());
        if (messages.isPresent()) {
            byte[] msg = (byte[]) messages.get();
            logger.info("接收到分布式框架消息对象：{}",msg);
            receiveService.processMessage(msg);
        }
    }
}
