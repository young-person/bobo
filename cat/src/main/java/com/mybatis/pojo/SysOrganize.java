package com.mybatis.pojo;

import java.io.Serializable;
import java.util.Date;



public class SysOrganize implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4800146529345895608L;

	private String id;

    private String name;

    private String code;

    private String brief;

    private Date createtime;

    private String parentid;

    private String instruction;
    
    @Override
	public String toString() {
		return "SysOrganize [id=" + id + ", name=" + name + ", code=" + code
				+ ", brief=" + brief + ", createtime=" + createtime
				+ ", parentid=" + parentid + ", instruction=" + instruction
				+ ", icon=" + icon + "]";
	}

	private String icon;
    
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction == null ? null : instruction.trim();
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}