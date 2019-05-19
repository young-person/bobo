package com.core.distributed.disruptor;

import com.lmax.disruptor.EventFactory;

public class CatTransactionEventFactory implements EventFactory<CatTransactionEvent> {

    @Override
    public CatTransactionEvent newInstance() {
        return new CatTransactionEvent();
    }
}
