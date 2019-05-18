package com.app.distributed.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.app.distributed.config.CatConfig;
import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.context.TransactionContextLocal;
import com.app.distributed.filter.TransactionAspectService;
import com.app.distributed.filter.TransactionInterceptor;
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
