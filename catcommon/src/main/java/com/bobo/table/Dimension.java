package com.bobo.table;

import java.util.Objects;

public class Dimension {

    private String id;
    private int level;
    private int nextLevel;
    private String value;

    private int len;
    private Dimension next;

    private int height;
    private int width;

    Dimension(String id, int level, int len){
        this.id = id;
        this.level = level;
        this.len = len;
    }

    Dimension(String id,int level,int len,Dimension next){
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
    @Override
    public String toString() {
        return "Dimension{" +
                "id='" + id + '\'' +
                ", level=" + level +
                ", nextLevel=" + nextLevel +
                ", value='" + value + '\'' +
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
                Objects.equals(next, dimension.next);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, level, nextLevel, value, len, next, height, width);
    }
}
