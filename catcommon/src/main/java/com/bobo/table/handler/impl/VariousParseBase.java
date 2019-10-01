package com.bobo.table.handler.impl;

import com.bobo.table.bean.Condition;
import com.bobo.table.handler.DataExecute;
import com.bobo.table.handler.data.VariousDataExecute;

public class VariousParseBase extends ParseBaseFactory {


    @Override
    public BaseidService getBaseidService() {
        return null;
    }

    /**
     * 解析条件参数 实体bean
     *
     * @param condition
     */
    @Override
    public void parse(Condition condition) {
        DataExecute<Condition> execute = new VariousDataExecute();
        execute.handler(condition);
    }
}
