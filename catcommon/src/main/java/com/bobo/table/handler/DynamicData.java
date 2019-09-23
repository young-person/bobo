package com.bobo.table.handler;

import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import com.bobo.table.bean.SimpleResult;

import java.util.List;

public interface DynamicData {
    /**
     * 根据维度和数据生成对应平铺数据
     * @param hdimension 水平X轴
     * @param vdimension 垂直Y轴
     * @param results SQL查询组装后数据集
     */
    void dynamicCreate(Dimension hdimension, Dimension vdimension, List<SimpleResult> results, Condition condition);
}
