package com.bobo.table.bean;

/**
 * 数据最小节点
 */
public class RNode {
    private int x;

    private int y;

    private Object value;

    private Dimension vdimension;

    private Dimension hdimension;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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
}
