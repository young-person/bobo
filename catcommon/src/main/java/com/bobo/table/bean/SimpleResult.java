package com.bobo.table.bean;

import java.util.List;
import java.util.Map;

public class SimpleResult {
    private List<Map<String,Object>> result;
    private List<Baseid> baseids;
    private String dimensions;
    private String sql;

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

    public List<Baseid> getBaseids() {
        return baseids;
    }

    public void setBaseids(List<Baseid> baseids) {
        this.baseids = baseids;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
