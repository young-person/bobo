package com.bobo.table.db;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 单表
 */
public class DBTable implements Serializable {
    private String tableName;
    private int size;

    private DBColumn[] primary;

    private List<DBColumn> columns;

    public DBTable(String tableName, int size, List<DBColumn> columns) {
        this.tableName = tableName;
        this.size = size;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "DBTable{" +
                "tableName='" + tableName + '\'' +
                ", size=" + size +
                ", primary=" + Arrays.toString(primary) +
                ", columns=" + columns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBTable)) return false;
        DBTable dbTable = (DBTable) o;
        return size == dbTable.size &&
                Objects.equals(tableName, dbTable.tableName) &&
                Arrays.equals(primary, dbTable.primary) &&
                Objects.equals(columns, dbTable.columns);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tableName, size, columns);
        result = 31 * result + Arrays.hashCode(primary);
        return result;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DBColumn[] getPrimary() {
        return primary;
    }

    public void setPrimary(DBColumn[] primary) {
        this.primary = primary;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }
}
