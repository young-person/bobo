package com.cloud.feign.catwebservice;

import com.feign.FeignService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.feign.FeignService.Service_catwebservice;

/**
 * 天气详情
 */
@FeignClient(Service_catwebservice)
public interface WeatherActionFeign extends FeignService {
    @PostMapping("/weather/city")
    public String getWeather(@RequestParam("name") String name);
}
