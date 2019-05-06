package com.bobo.table.bean;

import java.io.Serializable;
import java.util.Objects;

public class Baseid implements Serializable {
    /**
     * id为主键
     */
    private String id;
    private String table_col;
    private String explain;
    private String detail;
    private String type;

    private String sql;

    /**
     * 是否同期 临时符合主键
     */
    private boolean showtq = false;
    /**
     * 是否上期
     */
    private boolean showsq = false;



    public Baseid(String id) {
        this.id = id;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getSql() {
        return sql;
    }

    public boolean getShowtq() {
        return showtq;
    }

    public void setShowtq(boolean showtq) {
        this.showtq = showtq;
    }

    public boolean getShowsq() {
        return showsq;
    }

    public void setShowsq(boolean showsq) {
        this.showsq = showsq;
    }

    @Override
    public String toString() {
        return "Baseid{" +
                "id='" + id + '\'' +
                ", table_col='" + table_col + '\'' +
                ", explain='" + explain + '\'' +
                ", detail='" + detail + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baseid baseid = (Baseid) o;
        return id.equals(baseid.id) &&
                table_col.equals(baseid.table_col) &&
                explain.equals(baseid.explain) &&
                detail.equals(baseid.detail) &&
                type.equals(baseid.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, table_col, explain, detail, type);
    }
}
