package com.core.distributed.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.core.distributed.config.CatConfig;
import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import com.core.distributed.filter.TransactionAspectService;
import com.core.distributed.filter.TransactionInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

public class DubboTransactionInterceptor implements TransactionInterceptor {
    private final TransactionAspectService transactionAspectService;

    public DubboTransactionInterceptor(final TransactionAspectService transactionAspectService) {
        this.transactionAspectService = transactionAspectService;
    }
    @Override
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        final String context = RpcContext.getContext().getAttachment(CatConfig.LOCAL);
        TransactionContextBean transactionContext;
        if (StringUtils.isNoneBlank(context)) {
            transactionContext = JSONObject.parseObject(context,TransactionContextBean.class);
        } else {
            transactionContext = TransactionContextLocal.getInstance().get();
        }
        return transactionAspectService.invoke(transactionContext, pjp);
    }
}
