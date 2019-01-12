package com.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTokenInterceptor implements RequestInterceptor {
    private static Logger logger = LoggerFactory.getLogger(ClientTokenInterceptor.class);
    private final String clientId = "bobo-xxxx";//客户端ID
    private final String clientSecret = "xxxx-xxxx-xxxx-xxxx";//客户端加密
    private final String tokenHeader = "xt-bobo";
    private final String _ck = "win";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try{
            requestTemplate.header(tokenHeader,clientSecret,clientId,_ck);
        } catch (Exception e) {
            logger.error("认证拦截错误:{}",e.getMessage(),e);
        }
    }
}
