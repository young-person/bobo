package com.app.distributed.dubbo;

import com.app.distributed.AbstractTransactionAspect;
import com.app.distributed.filter.TransactionInterceptor;
import org.springframework.core.Ordered;

public class DubboTransactionAspect extends AbstractTransactionAspect implements Ordered {
    public DubboTransactionAspect(final TransactionInterceptor transactionInterceptor) {
        super.setTransactionInterceptor(transactionInterceptor);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
