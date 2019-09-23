package com.bobo.table.handler.data;

import com.bobo.table.bean.Condition;
import com.bobo.table.handler.DataExecute;

public class VariousDataExecute extends SqlDataExecute implements DataExecute<Condition> {
    /**
     * 执行处理多类别数据维度
     *
     * @param condition
     */
    @Override
    public void handler(Condition condition) {
        super.handler(condition);

    }
}
