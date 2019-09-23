package com.bobo.table.bean;

import java.io.Serializable;

public class TNode implements Serializable {
    protected String id;

    protected int x;

    protected int y;

    protected Dimension vdimension;

    protected Dimension hdimension;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
