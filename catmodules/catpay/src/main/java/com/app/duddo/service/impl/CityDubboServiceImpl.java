package com.app.duddo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.duddo.service.CityDubboService;
import com.mybatis.pojo.China;

@Service(version = "1.0.0")
public class CityDubboServiceImpl implements CityDubboService {

    public China findCityByName(String cityName) {
        China china =  new China();
        china.setCode("123");
        china.setParentcode("12");
        china.setName("温岭");
        return china;
    }
}
