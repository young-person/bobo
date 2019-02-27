package com.app.distributed.disruptor;

import com.app.distributed.CatTransaction;
import com.app.distributed.service.CoordinatorService;
import com.lmax.disruptor.EventHandler;

public class CatTransactionEventHandler implements EventHandler<CatTransactionEvent> {

    private final CoordinatorService service;


    public CatTransactionEventHandler(CoordinatorService service){
        this.service = service;
    }

    @Override
    public void onEvent(CatTransactionEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getType() == EventTypeEnum.SAVE.getCode()) {
            service.save(event.getTransaction());
        } else if (event.getType() == EventTypeEnum.UPDATE_PARTICIPANT.getCode()) {
            service.updateParticipant(event.getTransaction());
        } else if (event.getType() == EventTypeEnum.UPDATE_STATUS.getCode()) {
            final CatTransaction transaction = event.getTransaction();
            service.updateStatus(transaction.getTransId(), transaction.getStatus());
        } else if (event.getType() == EventTypeEnum.UPDATE_FAIR.getCode()) {
            service.updateFailTransaction(event.getTransaction());
        }
        event.clear();
    }
}
