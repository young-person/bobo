package com.app.distributed.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.app.distributed.context.TransactionContextBean;
import com.app.distributed.context.TransactionContextLocal;
import com.bobo.annotation.JTATransaction;
import com.bobo.enums.JTAEnum;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * duddo 分布式事物拦截器
 */
@Activate(group = { Constants.SERVER_KEY, Constants.CONSUMER})
public class TransactionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Class clazz = invoker.getInterface();
        String methodName = invocation.getMethodName();
        Class<?>[] args = invocation.getParameterTypes();

        Method method;
        JTATransaction transaction = null;
        try {
            method = clazz.getDeclaredMethod(methodName, args);
            transaction = method.getAnnotation(JTATransaction.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(transaction)) {
            final TransactionContextBean transactionContext = TransactionContextLocal.getInstance().get();
            if (Objects.nonNull(transactionContext)) {
                RpcContext.getContext().setAttachment(JTAEnum.TRANSACTION.getDesc() ,JSON.toJSONString(transaction));
            }
        }
        return invoker.invoke(invocation);
    }
}
