package com.bobo.table.handler;

import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;

import java.util.List;

/**
 * 数据查询
 */
public interface QueryParse extends Actuator{

    /**
     * 简单数据查询
     * @param type
     * @param sql
     * @param dimensions
     * @param baseids
     * @return
     */
    SimpleResult parse(String type, String sql, List<String> dimensions, List<Baseid> baseids);
}
