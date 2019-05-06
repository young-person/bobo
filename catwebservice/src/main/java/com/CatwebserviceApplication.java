package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
/**
 * 将微服务注册到Eureka Server服务中心 使用fetch
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CatwebserviceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CatwebserviceApplication.class, args);
    }
}
