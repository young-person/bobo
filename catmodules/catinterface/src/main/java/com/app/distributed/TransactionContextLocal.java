package com.app.distributed;

/**
 * 事务的上下文
 */
public class TransactionContextLocal {
    private static final ThreadLocal<TransactionContextBean> CURRENT_LOCAL = new ThreadLocal<>();

    private static final TransactionContextLocal TRANSACTION_CONTEXT_LOCAL = new TransactionContextLocal();

    private TransactionContextLocal() {}

    public static TransactionContextLocal getInstance() {
        return TRANSACTION_CONTEXT_LOCAL;
    }

    public void set(final TransactionContextBean context) {
        CURRENT_LOCAL.set(context);
    }

    public TransactionContextBean get() {
        return CURRENT_LOCAL.get();
    }

    public void remove() {
        CURRENT_LOCAL.remove();
    }
}
