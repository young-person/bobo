package com.mybatis.pojo;

public class SysDepartment {
    private Integer id;

    private Integer parent_id;

    private String name;

    private String code;

    private Integer sort;

    private String linkman;

    private String linkman_no;

    private String remark;

    @Override
	public String toString() {
		return "SysDepartment [id=" + id + ", parent_id=" + parent_id
				+ ", name=" + name + ", code=" + code + ", sort=" + sort
				+ ", linkman=" + linkman + ", linkman_no=" + linkman_no
				+ ", remark=" + remark + ", update_time=" + update_time
				+ ", update_id=" + update_id + ", create_time=" + create_time
				+ ", create_id=" + create_id + "]";
	}

	private String update_time;

    private Integer update_id;

    private String create_time;

    private Integer create_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getLinkman_no() {
        return linkman_no;
    }

    public void setLinkman_no(String linkman_no) {
        this.linkman_no = linkman_no == null ? null : linkman_no.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time == null ? null : update_time.trim();
    }

    public Integer getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public Integer getCreate_id() {
        return create_id;
    }

    public void setCreate_id(Integer create_id) {
        this.create_id = create_id;
    }
}