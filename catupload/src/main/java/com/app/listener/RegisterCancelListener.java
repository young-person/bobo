package com.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * 当一个服务退出或者挂掉监听事件
 */
@Configuration
public class RegisterCancelListener implements ApplicationListener<EurekaInstanceCanceledEvent> {
    private static final Logger logger = getLogger(RegisterCancelListener.class);
    @Override
    public void onApplicationEvent(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        logger.info("服务:{}挂了",eurekaInstanceCanceledEvent.getAppName());
    }
}
