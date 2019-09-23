package com.bobo.table.handler.data;

import com.bobo.table.bean.Condition;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.DataExecute;
import com.bobo.table.handler.DynamicData;
import com.bobo.table.handler.impl.DynamicDataDefault;

import java.util.List;

public class SingleDataExecute extends SqlDataExecute implements DataExecute<Condition> {
    /**
     * 执行处理多类别数据维度
     *
     * @param condition
     */
    @Override
    public void handler(Condition condition) {
        super.handler(condition);

        List<SimpleResult> results = query(condition);

        DynamicData dynamicData = new DynamicDataDefault();
        dynamicData.dynamicCreate(condition.getHdimensions().get(0),condition.getVdimensions().get(0),results,condition);
    }

}
