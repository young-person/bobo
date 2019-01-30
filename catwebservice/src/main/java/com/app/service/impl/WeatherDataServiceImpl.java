package com.app.service.impl;

import com.app.Const;
import com.app.pojo.WeatherResponse;
import com.app.service.WeatherDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    @Autowired
    @Qualifier("netRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = Const.WEATHER_URI + "citykey=" + cityId;
        return this.doGetWeather(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = Const.WEATHER_URI + "city=" + cityName;
        return this.doGetWeather(uri);
    }


    private WeatherResponse doGetWeather(String uri) {
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null;
        String strBody = null;

        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        try {
            resp = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }


}
