package com.bobo.table.bean;

import com.bobo.table.db.DBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dimension implements Cloneable{

    private String id;
    private int level;
    private int nextLevel;
    private String value;
    private String name;

    private List<DBean> result = new ArrayList<DBean>();

    private int len;
    private Dimension next;

    private int height;
    private int width;


    public Dimension(String id, int level, int len){
        this.id = id;
        this.level = level;
        this.len = len;
    }

    public Dimension(String id,int level,int len,Dimension next){
        this.id = id;
        this.level = level;
        this.len = len;
        this.next = next;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Dimension getNext() {
        return next;
    }

    public void setNext(Dimension next) {
        this.next = next;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DBean> getResult() {
        return result;
    }

    public void setResult(List<DBean> result) {
        this.result = result;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Dimension clone() {
        Dimension dimension = null;
        try {
            dimension = (Dimension) super.clone();
            return dimension;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "id='" + id + '\'' +
                ", level=" + level +
                ", nextLevel=" + nextLevel +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", result=" + result +
                ", len=" + len +
                ", next=" + next +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dimension)) return false;
        Dimension dimension = (Dimension) o;
        return level == dimension.level &&
                nextLevel == dimension.nextLevel &&
                len == dimension.len &&
                height == dimension.height &&
                width == dimension.width &&
                Objects.equals(id, dimension.id) &&
                Objects.equals(value, dimension.value) &&
                Objects.equals(name, dimension.name) &&
                Objects.equals(result, dimension.result) &&
                Objects.equals(next, dimension.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, nextLevel, value, name, result, len, next, height, width);
    }
}
