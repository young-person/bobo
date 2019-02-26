package com.app.pay.listener;

import com.app.common.BaseInterface;
import com.bobo.annotation.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // root application context
        if(null == contextRefreshedEvent.getApplicationContext().getParent()) {
            logger.debug(">>>>> spring初始化完毕 <<<<<");
            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for(Object service : baseServices.values()) {
                logger.debug(">>>>> {}.initMapper()", service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    logger.error("初始化BaseService的initMapper方法异常", e);
                    e.printStackTrace();
                }
            }

            // 系统入口初始化
            Map<String, BaseInterface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            for(Object service : baseInterfaceBeans.values()) {
                logger.debug(">>>>> {}.init()", service.getClass().getName());
                try {
                    Method init = service.getClass().getMethod("init");
                    init.invoke(service);
                } catch (Exception e) {
                    logger.error("初始化BaseInterface的init方法异常", e);
                    e.printStackTrace();
                }
            }

        }
    }

}
