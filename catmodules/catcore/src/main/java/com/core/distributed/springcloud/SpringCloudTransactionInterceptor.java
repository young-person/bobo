
package com.core.distributed.springcloud;

import com.alibaba.fastjson.JSONObject;
import com.core.distributed.context.TransactionContextBean;
import com.core.distributed.context.TransactionContextLocal;
import com.core.distributed.filter.TransactionAspectService;
import com.core.distributed.filter.TransactionInterceptor;
import com.bobo.enums.JTAEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SpringCloudTransactionInterceptor implements TransactionInterceptor {

    private final TransactionAspectService transactionAspectService;

    public SpringCloudTransactionInterceptor(final TransactionAspectService transactionAspectService) {
        this.transactionAspectService = transactionAspectService;
    }

    @Override
    public Object interceptor(final ProceedingJoinPoint pjp) throws Throwable {
        TransactionContextBean transactionContext = TransactionContextLocal.getInstance().get();
        if (Objects.nonNull(transactionContext) && transactionContext.getRole() == JTAEnum.LOCAL.getCode()) {
            transactionContext = TransactionContextLocal.getInstance().get();
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
            String context = request == null ? null : request.getHeader("");
            if (StringUtils.isNoneBlank(context)) {
                transactionContext = JSONObject.parseObject(context,TransactionContextBean.class);
            }
        }
        return transactionAspectService.invoke(transactionContext,pjp);
    }

}
