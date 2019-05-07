package com.study.bean.springBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class MySpringBeanPostProcessor implements BeanPostProcessor {
	
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MySpringBean) {
            System.out.println("BeanPostProcessor-postProcessAfterInitialization......");
        }
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MySpringBean) {
            System.out.println("BeanPostProcessor-postProcessBeforeInitialization......");
        }
        return bean;
    }
	
}
