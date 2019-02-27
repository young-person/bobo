package com.app.distributed.service;

public interface MqTransactionMessageService {
    void sendMessage(String destination, Integer pattern, byte[] message);
}
