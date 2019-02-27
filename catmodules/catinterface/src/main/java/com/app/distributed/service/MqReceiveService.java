package com.app.distributed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MqReceiveService {
    public static final Logger logger = LoggerFactory.getLogger(MqReceiveService.class);
    Boolean processMessage(byte[] message);
}
