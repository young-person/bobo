package com.app.controller;

import com.cloud.feign.catwebservice.WeatherActionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 远程调用类
 */
@RestController
public class RemoteController {
    @Autowired
    private WeatherActionFeign feignWeather;

    public void name(){
        feignWeather.getWeather("武汉");
    }
}
