package com.study.bean.start;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.study.bean.configBean.ConfigBean;
import com.study.bean.springBean.MySpringBean;


public class Start {
	public static void main(String[] args) {
		ApplicationContext beans = new AnnotationConfigApplicationContext(ConfigBean.class);
		MySpringBean mySpringBean = beans.getBean(MySpringBean.class);
		System.out.println(mySpringBean);
	}
}
