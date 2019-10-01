package com.bobo.table.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 作为每一个数据列点
 */
public final class Baseid implements Serializable {
    /**
     * id为主键
     */
    private String id;
    /**
     * 每个点对应得sql
     */
    private String sql;

    /**
     * 统计sql对应明细sql
     */
    private volatile String realSql;

    /**
     *明细表对应得 表字段table.column
     */
    private String table_col;
    /**
     *说明
     */
    private String explain;


    /**
     * 解析开始位置
     */
    private int start;
    /**
     * 解析结束位置
     */
    private int end;

    /**
     * 是否同期 临时符合主键
     */
    private boolean showtq = false;
    /**
     * 是否上期
     */
    private boolean showsq = false;

    /**
     * 每个点对应条件
     */
    private Map<String,Object> condition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (Objects.isNull(id) && id.isEmpty()){
            throw new RuntimeException("Baseid is id not allowed null...");
        }
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getRealSql() {
        return realSql;
    }

    public void setRealSql(String realSql) {
        this.realSql = realSql;
    }

    public String getTable_col() {
        return table_col;
    }

    public void setTable_col(String table_col) {
        this.table_col = table_col;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isShowtq() {
        return showtq;
    }

    public void setShowtq(boolean showtq) {
        this.showtq = showtq;
    }

    public boolean isShowsq() {
        return showsq;
    }

    public void setShowsq(boolean showsq) {
        this.showsq = showsq;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }
}
