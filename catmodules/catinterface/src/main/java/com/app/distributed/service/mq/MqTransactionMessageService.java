package com.app.distributed.service.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MqTransactionMessageService {
    public static final Logger logger = LoggerFactory.getLogger(MqTransactionMessageService.class);
    void sendMessage(String destination, Integer pattern, byte[] message);
}
