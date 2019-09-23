package com.bobo.table.bean;

import java.util.*;

/**
 * 条件
 */
final public class Condition {
    /**
     * x轴维度
     */
    private List<Dimension> vdimensions = new ArrayList<>();
    /**
     * y轴维度
     */
    private List<Dimension> hdimensions = new ArrayList<>();
    /**
     * 携带附加参数
     */
    private Map<String,String> condMap = new HashMap<>();

    /**
     * 数据表格每一列的表达式
     */
    private List<String> corns = new ArrayList<>();

    /**
     * 数据对照映射关系
     */
    private LinkedHashMap<String, List<Baseid>> relativeMap = new LinkedHashMap<>();
    /**
     * 表达式解析之后的baseid
     */
    private List<Baseid> baseids = new ArrayList<>();

    public Condition(){}

    public List<Dimension> getVdimensions() {
        return vdimensions;
    }

    public void setVdimensions(List<Dimension> vdimensions) {
        this.vdimensions = vdimensions;
    }

    public List<Dimension> getHdimensions() {
        return hdimensions;
    }

    public void setHdimensions(List<Dimension> hdimensions) {
        this.hdimensions = hdimensions;
    }

    public Map<String, String> getCondMap() {
        return condMap;
    }

    public void setCondMap(Map<String, String> condMap) {
        this.condMap = condMap;
    }

    public LinkedHashMap<String, List<Baseid>> getRelativeMap() {
        return relativeMap;
    }

    public void setRelativeMap(LinkedHashMap<String, List<Baseid>> relativeMap) {
        this.relativeMap = relativeMap;
    }
    public List<String> getCorns() {
        return corns;
    }

    public void setCorns(List<String> corns) {
        this.corns = corns;
    }
    public List<Baseid> getBaseids() {
        return baseids;
    }

    public void setBaseids(List<Baseid> baseids) {
        this.baseids = baseids;
    }
}
