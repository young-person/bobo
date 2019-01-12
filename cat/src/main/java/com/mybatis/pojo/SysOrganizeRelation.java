package com.mybatis.pojo;

public class SysOrganizeRelation {
    private String id;

    private String relationid;

    private Integer level;

    public String getId() {
        return id;
    }

    @Override
	public String toString() {
		return "SysOrganizeRelation [id=" + id + ", relationid=" + relationid
				+ ", level=" + level + "]";
	}

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRelationid() {
        return relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid == null ? null : relationid.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}