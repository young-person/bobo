package com.app.dictionary.templet.model;

import java.io.Serializable;

public class Column implements Serializable {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 值长度
     */
    private String length;
    /**
     * 所属表
     */
    private String tableName;
    /**
     * 是否为空
     */
    private String non;
    /**
     * 字段解释
     */
    private String description;
    /**
     * 类型
     */
    private String type;
    /**
     * 说明
     */
    private String detail;

    private Column(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNon() {
        return non;
    }

    public void setNon(String non) {
        this.non = non;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
