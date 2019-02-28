package com.app.distributed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mq 收到消息
 */
public interface MqReceiveService {
    public static final Logger logger = LoggerFactory.getLogger(MqReceiveService.class);
    Boolean processMessage(byte[] message);
}
