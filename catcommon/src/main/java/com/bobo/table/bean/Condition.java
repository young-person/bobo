package com.bobo.table.bean;

import java.util.*;

/**
 * 条件
 */
public class Condition {
    private String sql;
    private Dimension vdimension;
    private Dimension hdimension;
    private Map<String,String> condMap;

    private List<Baseid> baseids = new ArrayList<>();//表达式解析之后的baseid
    private List<String> corn = new ArrayList<>();//表达式
    /**
     * 数据对照映射关系
     */
    private LinkedHashMap<String,List<Baseid>> relativeMap = new LinkedHashMap<>();

    public Condition(){}

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Dimension getVdimension() {
        return vdimension;
    }

    public void setVdimension(Dimension vdimension) {
        this.vdimension = vdimension;
    }

    public Dimension getHdimension() {
        return hdimension;
    }

    public void setHdimension(Dimension hdimension) {
        this.hdimension = hdimension;
    }

    public Map<String, String> getCondMap() {
        return condMap;
    }

    public void setCondMap(Map<String, String> condMap) {
        this.condMap = condMap;
    }

    public List<Baseid> getBaseids() {
        return baseids;
    }

    public void setBaseids(List<Baseid> baseids) {
        this.baseids = baseids;
    }

    public List<String> getCorn() {
        return corn;
    }

    public void setCorn(List<String> corn) {
        this.corn = corn;
    }

    public LinkedHashMap<String, List<Baseid>> getRelativeMap() {
        return relativeMap;
    }

    public void setRelativeMap(LinkedHashMap<String, List<Baseid>> relativeMap) {
        this.relativeMap = relativeMap;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "sql='" + sql + '\'' +
                ", vdimension=" + vdimension +
                ", hdimension=" + hdimension +
                ", condMap=" + condMap +
                ", baseids=" + baseids +
                ", corn=" + corn +
                ", relativeMap=" + relativeMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Condition)) return false;
        Condition condition = (Condition) o;
        return Objects.equals(sql, condition.sql) &&
                Objects.equals(vdimension, condition.vdimension) &&
                Objects.equals(hdimension, condition.hdimension) &&
                Objects.equals(condMap, condition.condMap) &&
                Objects.equals(baseids, condition.baseids) &&
                Objects.equals(corn, condition.corn) &&
                Objects.equals(relativeMap, condition.relativeMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, vdimension, hdimension, condMap, baseids, corn, relativeMap);
    }
}
