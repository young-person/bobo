package com.cloud.center.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * 新服务注册事件监听
 */
@Configuration
public class RegisterRenewListener implements ApplicationListener<EurekaInstanceRenewedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterRenewListener.class);
    @Override
    public void onApplicationEvent(EurekaInstanceRenewedEvent eurekaInstanceRenewedEvent) {
        logger.info("心跳检测服务：{}" ,eurekaInstanceRenewedEvent.getInstanceInfo().getAppName());
    }
}
