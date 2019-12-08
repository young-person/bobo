package com.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatWebServiceProperty {

    @Value("${catwebservice.crawler.path}")
    private String crawlerPath;

    @Value("${catwebservice.cat.collocation}")
    private String collocation;

    @Value("${catwebservice.userName}")
    private String userName;

    @Value("${catwebservice.password}")
    private String password;


    public String getCrawlerPath() {
        return crawlerPath;
    }

    public String getCollocation() {
        return collocation;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
