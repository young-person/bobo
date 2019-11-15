package com.app.crawler.url;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 抓取链接定义
 */
public enum Linking {
	SYSTEMPATH("/config/","系统配置文件夹"),
    WEATHERURL("http://www.weather.com.cn/textFC/hb.shtml","天气数据抓取链接"),
    DEPARTMENT("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html","行政区划统计分析"),
    MEDICINE("https://db.yaozh.com/zhongyaocai","中国中医药材数据");



    private String url;
    private String name;


    Linking(String url,String name) {
        this.url = url;
        this.name = name;
    }

    public static Linking acquire(final String url) {
        Optional<Linking> serializeEnum =
                Arrays.stream(Linking.values())
                        .filter(v -> Objects.equals(v.url, url))
                        .findFirst();
        return serializeEnum.orElse(Linking.WEATHERURL);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
