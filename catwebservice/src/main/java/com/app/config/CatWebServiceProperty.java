package com.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatWebServiceProperty {

    @Value("${catwebservice.crawler.path}")
    private String crawlerPath;

    public String getCrawlerPath() {
        return crawlerPath;
    }

    public void setCrawlerPath(String crawlerPath) {
        this.crawlerPath = crawlerPath;
    }
}
