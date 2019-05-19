package com.core.distributed.service.mq;

import com.core.distributed.CatTransaction;

public interface SendMessageService {
    public boolean sendMessage(CatTransaction transaction);
}
