package com.bobo.table.handler.impl;

import com.bobo.enums.CEnum;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.AbstractQueryParse;

import javax.sql.DataSource;
import java.util.List;

public class ParseSumResult extends AbstractQueryParse<CEnum> {

    @Override
    public SimpleResult parse(CEnum cEnum,String where,String dimensions,List<Baseid> baseids,String from) {
        StringBuilder builder = new StringBuilder("select");

        for(Baseid b : baseids){
            builder.append("sum(");
            builder.append(b.getTable_col().split(".")[1]);
            builder.append(" as ");
            builder.append(b.getId());
            builder.append(") ");
        }
        builder.append(" from ");
        builder.append(from);
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.setSql(builder.toString());
        simpleResult.setBaseids(baseids);
        simpleResult.setDimensions(dimensions);
        simpleResult.setResult(queryExec(builder.toString()));
        return null;
    }

    @Override
    public DataSource getDynamicDataSource() {
        return null;
    }
}
