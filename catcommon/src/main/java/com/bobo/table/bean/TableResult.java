package com.bobo.table.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单结果数据集
 */
public class TableResult {

    /**
     * 父节点
     */
    private TableResult parent;

    /**
     * 数据集
     */
    List<List<RNode>> datas = new ArrayList<>();

    public TableResult getParent() {
        return parent;
    }

    public void setParent(TableResult parent) {
        this.parent = parent;
    }

    public List<List<RNode>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<RNode>> datas) {
        this.datas = datas;
    }
}
