package com.cloud.feign.catupload.fit;

import com.bobo.domain.AuthUser;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallbackFactoryCustomer implements FallbackFactory<AuthUser> {
    private static Logger logger = LoggerFactory.getLogger(FallbackFactoryCustomer.class);
    @Override
    public AuthUser create(Throwable throwable) {
        logger.error("fallback reason:{}",throwable.getMessage());
        AuthUser user = new AuthUser();
        return user;
    }
}
