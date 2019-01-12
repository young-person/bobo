package com.app.controller;

import com.app.pojo.WeatherResponse;
import com.app.service.WeatherDataService;
import com.app.service.impl.WeatherDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherDataServiceImpl weatherDataService;
    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> queryCityName(String name){
        System.out.println(name);
        WeatherResponse weatherResponse = weatherDataService.getDataByCityName(name);
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }
}
