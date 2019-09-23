package com.bobo.table.bean;

import java.util.List;
import java.util.Map;

public class SimpleResult {

    protected List<Map<String,Object>> result;
    protected String dimensions;
    protected String sql;

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
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
