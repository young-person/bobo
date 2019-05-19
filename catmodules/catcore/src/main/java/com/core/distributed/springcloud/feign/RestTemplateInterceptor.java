
package com.core.distributed.springcloud.feign;

import com.alibaba.fastjson.JSON;
import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class RestTemplateInterceptor implements RequestInterceptor {

    private static final String header = "TRANSACTION_CONTEXT";
    @Override
    public void apply(final RequestTemplate requestTemplate) {
        final TransactionContextBean transactionContext = TransactionContextLocal.getInstance().get();
        requestTemplate.header(header,JSON.toJSONString(transactionContext));
    }

}
