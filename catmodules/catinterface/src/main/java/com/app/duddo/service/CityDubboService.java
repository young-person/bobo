package com.app.duddo.service;

import com.mybatis.pojo.China;

public interface CityDubboService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    China findCityByName(String cityName);
}
