package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
/**
 * 将微服务注册到Eureka Server服务中心 使用fetch
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CatwebserviceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CatwebserviceApplication.class, args);
    }

}
