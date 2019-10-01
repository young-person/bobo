package com.bobo.table.handler.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.AbstractQueryParse;

import java.util.List;
import java.util.Map;

public class ParseSumResult extends AbstractQueryParse {

    @Override
    public SimpleResult parse(String type,String sql,List<String> dimensions,List<Baseid> baseids) {
        StringBuilder builder = new StringBuilder("select");

        for(Baseid b : baseids){
            builder.append("sum(");
            builder.append(b.getTable_col().split(".")[1]);
            builder.append(" as ");
            builder.append(b.getId());
            builder.append(") ");
        }
        builder.append(" from ");
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.setSql(builder.toString());
//        simpleResult.setBaseids(baseids);
        simpleResult.setResult(queryExecute(builder.toString()));
        return simpleResult;
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
