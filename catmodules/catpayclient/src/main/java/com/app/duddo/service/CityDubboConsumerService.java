package com.app.duddo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mybatis.pojo.China;
import org.springframework.stereotype.Component;

@Component
public class CityDubboConsumerService {

    @Reference
    CityDubboService cityDubboService;

    public void printCity() {
        String cityName="温岭";
        China city = cityDubboService.findCityByName(cityName);
        System.out.println(city.toString());
    }
}
