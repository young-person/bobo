package com.bobo.table.handler.impl;

import com.bobo.table.db.DBean;
import com.bobo.table.handler.Actuator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class ActuatorImpl extends Actuator<DBean> {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public DBean exec(String sql) {
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return null;
    }

}
