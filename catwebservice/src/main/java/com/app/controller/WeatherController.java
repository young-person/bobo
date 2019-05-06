package com.app.controller;

import com.app.pojo.WeatherResponse;
import com.app.service.impl.WeatherDataServiceImpl;
import com.cloud.feign.catwebservice.WeatherActionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController implements WeatherActionFeign {
    @Autowired
    private WeatherDataServiceImpl weatherDataService;
    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> queryCityName(String name){
        WeatherResponse weatherResponse = weatherDataService.getDataByCityName(name);
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }

    @PostMapping("/weather/city")
    @Override
    public String getWeather(@RequestParam("name") String name){
        WeatherResponse weatherResponse = weatherDataService.getDataByCityName(name);
        System.out.println(name);
        return name;
    }



    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
