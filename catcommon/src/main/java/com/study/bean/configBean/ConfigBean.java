package com.study.bean.configBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.study.bean.springBean.MySpringBean;
import com.study.bean.springBean.MySpringBeanPostProcessor;

@Configuration
public class ConfigBean {
	@Bean(name = "mySpringBean")
    public MySpringBean getMySpringBean(){
        return  new MySpringBean();
    }
	
	@Bean(name = "mySpringBeanPostProcessor")
    public MySpringBeanPostProcessor getMySpringBeanPostProcessor(){
        return  new MySpringBeanPostProcessor();
    }
}
