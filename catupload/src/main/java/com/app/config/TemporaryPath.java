package com.app.config;

import com.aliyun.oss.OSSClient;
import com.bobo.constant.CProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class TemporaryPath {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("d://temp");
        return factory.createMultipartConfig();
    }
    @Bean
    public OSSClient getOSSClient(){
        OSSClient client = new OSSClient(CProperties.ALIYUN_OSS_ENDPOINT,CProperties.ALIYUN_OSS_ACCESSKEYID,
                CProperties.ALIYUN_OSS_ACCESSKEYSECRET);
        return client;
    }
}
