package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
/**
 * 将微服务注册到Eureka Server服务中心 使用fetch
 */
@EnableDiscoveryClient
@EnableFeignClients
/**
 * 使用断路由避免整体服务坍塌
 */
@EnableHystrix
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
