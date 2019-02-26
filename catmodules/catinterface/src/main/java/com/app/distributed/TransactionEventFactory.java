package com.app.distributed;

import com.lmax.disruptor.EventFactory;

public class TransactionEventFactory implements EventFactory<CatTransactionEvent> {

    @Override
    public CatTransactionEvent newInstance() {
        return new CatTransactionEvent();
    }
}
