package com.app;

import java.net.InetAddress;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.app.duddo.service.CityDubboConsumerService;

@SpringBootApplication
public class CatpayclientApplication implements CommandLineRunner{
    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext run = SpringApplication.run(CatpayclientApplication.class, args);
        CityDubboConsumerService cityService = run.getBean(CityDubboConsumerService.class);
        cityService.printCity();
    }
    
    @Override
    public void run(String... strings) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
    }
}
