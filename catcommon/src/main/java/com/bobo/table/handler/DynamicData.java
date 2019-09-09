package com.bobo.table.handler;

import com.bobo.table.bean.Dimension;
import com.bobo.table.bean.SimpleResult;

import java.util.List;

public interface DynamicData {
    /**
     * 根据维度和数据生成对应平铺数据
     * @param hdimension
     * @param vdimension
     * @param results
     */
    void dynamicCreate(Dimension hdimension, Dimension vdimension,String dimensions, List<SimpleResult> results);
}
