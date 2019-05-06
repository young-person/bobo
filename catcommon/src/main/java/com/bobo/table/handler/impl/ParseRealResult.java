package com.bobo.table.handler.impl;

import com.bobo.enums.CEnum;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.AbstractQueryParse;

import javax.sql.DataSource;
import java.util.List;

public class ParseRealResult extends AbstractQueryParse<CEnum> {

    @Override
    public SimpleResult parse(CEnum cEnum,String where,String dimensions,List<Baseid> baseids,String from) {

        SimpleResult result = new SimpleResult();
        result.setBaseids(baseids);
        result.setDimensions(dimensions);
        result.setSql(where);
        result.setResult(queryExec(where));
        return result;
    }

    @Override
    public DataSource getDynamicDataSource() {
        return null;
    }



}
