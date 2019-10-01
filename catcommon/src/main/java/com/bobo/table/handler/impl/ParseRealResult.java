package com.bobo.table.handler.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.AbstractQueryParse;

import java.util.List;
import java.util.Map;

public class ParseRealResult extends AbstractQueryParse {

    @Override
    public SimpleResult parse(String type,String sql,List<String> dimensions,List<Baseid> baseids) {

        SimpleResult result = new SimpleResult();
//        result.setBaseids(baseids);
        result.setDimensions(String.join(",",dimensions));
        result.setSql(sql);
        result.setResult(queryExecute(sql));
        return result;
    }

    /**
     * sql 执行  获取唯独 代码信息数据
     *
     * @param sql
     * @return
     */
    @Override
    public List<Map<String, Object>> executeSql(String sql) {
        return null;
    }

    @Override
    public DruidDataSource getDynamicDataSource() {
        return null;
    }



}
