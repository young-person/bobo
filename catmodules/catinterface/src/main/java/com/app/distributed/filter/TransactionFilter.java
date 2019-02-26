package com.app.distributed.filter;

import com.alibaba.dubbo.rpc.*;

/**
 * duddo 分布式事物拦截器
 */
public class TransactionFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        invoker.getInterface();

        String methodName = invocation.getMethodName();



        return null;
    }
}
