package com.study.bean.springBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 */
public class MySpringBean implements BeanNameAware, BeanFactoryAware, InitializingBean, ApplicationContextAware {
	
	 private ApplicationContext applicationContext;
	
	public MySpringBean() {
        System.out.println("new MySpringBean......");
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println("ApplicationContextAware-setApplicationContext......");
        this.applicationContext = context;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean-afterPropertiesSet......");
    }

    public void setBeanFactory(BeanFactory bf) throws BeansException {
        System.out.println("BeanFactoryAware-setBeanFactory......");
    }

    public void setBeanName(String name) {
        System.out.println("BeanNameAware-setBeanName......");
    }

    public void init() {
        System.out.println("init-method......");
    }
	
	
}
