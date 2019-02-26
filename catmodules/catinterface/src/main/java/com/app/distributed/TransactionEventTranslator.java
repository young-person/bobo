package com.app.distributed;

import com.lmax.disruptor.EventTranslatorOneArg;

public class TransactionEventTranslator implements EventTranslatorOneArg<CatTransactionEvent, CatTransaction> {

    private int type;

    public TransactionEventTranslator(final int type) {
        this.type = type;
    }

    @Override
    public void translateTo(final CatTransactionEvent transactionEvent, final long l,final CatTransaction transaction) {
        transactionEvent.setTransaction(transaction);
        transactionEvent.setType(type);
    }
}
