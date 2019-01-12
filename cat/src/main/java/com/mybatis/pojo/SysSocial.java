package com.mybatis.pojo;

import java.util.Date;

public class SysSocial {
    private String id;

    private String name;

    private String code;

    private Float x;

    private Float y;

    @Override
	public String toString() {
		return "SysSocial [id=" + id + ", name=" + name + ", code=" + code
				+ ", x=" + x + ", y=" + y + ", z=" + z + ", size=" + size
				+ ", createtime=" + createtime + "]";
	}

	private Float z;

    private Double size;

    private Date createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}