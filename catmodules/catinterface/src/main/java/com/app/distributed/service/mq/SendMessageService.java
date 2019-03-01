package com.app.distributed.service.mq;

import com.app.distributed.CatTransaction;

public interface SendMessageService {
    public boolean sendMessage(CatTransaction transaction);
}
