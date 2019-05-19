package com.core.distributed.dubbo;

import com.core.distributed.AbstractTransactionAspect;
import com.core.distributed.filter.TransactionInterceptor;
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
