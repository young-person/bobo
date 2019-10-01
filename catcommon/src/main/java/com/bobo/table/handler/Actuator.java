package com.bobo.table.handler;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.List;
import java.util.Map;

public interface Actuator {

    /**
     * sql 执行  获取唯独 代码信息数据
     * @param sql
     * @return
     */
    List<Map<String,Object>> executeSql(String sql);

    /**
     * 获取数据源
     * @return
     */
    DruidDataSource getDynamicDataSource();

}
