package com.core.consumer;

import com.core.distributed.service.mq.ReceiveMessageService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@RabbitListener(queues = "inventory",containerFactory = "containerFactory")
@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "host")
public class RabbitmqConsumer {
    private final ReceiveMessageService receiveService;

    public RabbitmqConsumer(ReceiveMessageService receiveService) {
        this.receiveService = receiveService;
    }

    @RabbitHandler
    public void process(byte[] msg) {
       boolean sure = receiveService.processMessage(msg);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.rabbitmq", name = "host")
    public SimpleRabbitListenerContainerFactory getContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(100);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventory");
    }
}