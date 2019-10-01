package com.bobo.table.handler.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.bobo.table.handler.Actuator;

import java.util.List;
import java.util.Map;

public class ActuatorImpl implements Actuator {

    /**
     * sql 执行  获取唯独 代码信息数据
     *
     * @param sql
     * @return
     */
    @Override
    public List<Map<String, Object>> executeSql(String sql) {
        //        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
//        List<DBean> beans = new ArrayList<>();
//        for(Map<String,Object> map : list){
//            DBean dbean = new DBean();
//            dbean.setCode((String)map.get("code"));
//            dbean.setName((String)map.get("name"));
//            beans.add(dbean);
//        }
        return null;
    }

    /**
     * 获取数据源
     *
     * @return
     */
    @Override
    public DruidDataSource getDynamicDataSource() {
        return null;
    }

}
