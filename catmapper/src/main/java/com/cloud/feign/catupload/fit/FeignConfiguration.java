package com.cloud.feign.catupload.fit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请求拦截器 验证类
 */
@Configuration
public class FeignConfiguration {
    @Bean
    public ClientTokenInterceptor getClientTokenInterceptor(){
        return new ClientTokenInterceptor();
    }
}
