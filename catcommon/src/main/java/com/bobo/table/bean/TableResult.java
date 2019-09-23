package com.bobo.table.bean;

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
    private TNode[][] datas= null;

    public TableResult getParent() {
        return parent;
    }

    public void setParent(TableResult parent) {
        this.parent = parent;
    }
    public TNode[][] getDatas() {
        return datas;
    }

    public void setDatas(TNode[][] datas) {
        this.datas = datas;
    }
}
