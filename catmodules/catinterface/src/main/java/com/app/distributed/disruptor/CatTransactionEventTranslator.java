package com.app.distributed.disruptor;

import com.app.distributed.CatTransaction;
import com.lmax.disruptor.EventTranslatorOneArg;

public class CatTransactionEventTranslator implements EventTranslatorOneArg<CatTransactionEvent, CatTransaction> {

    private int type;

    public CatTransactionEventTranslator(final int type) {
        this.type = type;
    }

    @Override
    public void translateTo(final CatTransactionEvent transactionEvent, final long l,final CatTransaction transaction) {
        transactionEvent.setTransaction(transaction);
        transactionEvent.setType(type);
    }
}
