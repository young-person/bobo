package com.app.distributed.service;

import com.app.distributed.CatTransaction;

public interface TransactionMessageService {
    public boolean sendMessage(CatTransaction transaction);
}
