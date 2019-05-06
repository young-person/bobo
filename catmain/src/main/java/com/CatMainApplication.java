package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@SpringBootApplication

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class CatMainApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CatMainApplication.class, args);
    }
}


