package com.core.distributed.springcloud;

import com.core.distributed.AbstractTransactionAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringCloudTransactionAspect extends AbstractTransactionAspect implements Ordered {

    @Autowired
    public SpringCloudTransactionAspect(final SpringCloudTransactionInterceptor springCloudMythTransactionInterceptor) {
        this.setTransactionInterceptor(springCloudMythTransactionInterceptor);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
