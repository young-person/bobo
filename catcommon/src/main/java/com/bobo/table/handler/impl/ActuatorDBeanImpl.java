package com.bobo.table.handler.impl;

import com.bobo.table.db.DBean;
import com.bobo.table.handler.Actuator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ActuatorDBeanImpl extends Actuator<List<DBean>> {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public List<DBean> exec(String sql) {
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<DBean> beans = new ArrayList<>();
        for(Map<String,Object> map : list){
            DBean dbean = new DBean();
            dbean.setCode((String)map.get("code"));
            dbean.setName((String)map.get("name"));
            beans.add(dbean);
        }
        return beans;
    }
}
