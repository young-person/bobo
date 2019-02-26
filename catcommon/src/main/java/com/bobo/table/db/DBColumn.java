package com.bobo.table.db;

import com.bobo.enums.IndexEnum;

import java.util.Objects;

/**
 * 字段
 */
public class DBColumn {
    private String name;
    private String type;
    private boolean isnull;
    private IndexEnum cindex = IndexEnum.NONE;

    public DBColumn(String name){
        this.name = name;
    }

    public DBColumn(String name,String cindex){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsnull() {
        return isnull;
    }

    public void setIsnull(boolean isnull) {
        this.isnull = isnull;
    }
    public IndexEnum getCindex() {
        return cindex;
    }

    public void setCindex(IndexEnum cindex) {
        this.cindex = cindex;
    }
    @Override
    public String toString() {
        return "DBColumn{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isnull=" + isnull +
                ", cindex=" + cindex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBColumn)) return false;
        DBColumn dbColumn = (DBColumn) o;
        return isnull == dbColumn.isnull &&
                Objects.equals(name, dbColumn.name) &&
                Objects.equals(type, dbColumn.type) &&
                cindex == dbColumn.cindex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isnull, cindex);
    }
}
