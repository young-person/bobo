package com.test;


import org.springframework.beans.factory.InitializingBean;

/**
 * 在spring的bean初始化做一些事情
 *
 * springbean的生命周期
 * BeanFactoryPostProcesser
 */
public class InitBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
//        BeanFactoryPostProcessor
       // InstantiationAwareBeanPostProcessor
    }

}
