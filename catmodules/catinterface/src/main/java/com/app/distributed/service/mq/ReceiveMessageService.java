package com.app.distributed.service.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mq 收到消息
 */
public interface ReceiveMessageService {
    public static final Logger logger = LoggerFactory.getLogger(ReceiveMessageService.class);
    Boolean processMessage(byte[] message);
}
