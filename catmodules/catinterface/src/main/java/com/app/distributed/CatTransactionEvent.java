package com.app.distributed;

import java.io.Serializable;

public class CatTransactionEvent implements Serializable {
    private static final long serialVersionUID = -6965109161383975860L;
    private CatTransaction transaction;

    private int type;

    public void clear() {
        transaction = null;
    }


    @Override
    public String toString() {
        return "CatTransactionEvent{" +
                "transaction=" + transaction +
                ", type=" + type +
                '}';
    }
    public CatTransactionEvent() {}
    public CatTransactionEvent(CatTransaction transaction, int type) {
        this.transaction = transaction;
        this.type = type;
    }

    public CatTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(CatTransaction transaction) {
        this.transaction = transaction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
