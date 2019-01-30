package com.mybatis.pojo;

public class SysDict {
    private Integer dict_id;

    private String dict_name;

    private String dict_type;

    private String dict_remark;

    public Integer getDict_id() {
        return dict_id;
    }

    public void setDict_id(Integer dict_id) {
        this.dict_id = dict_id;
    }

    @Override
	public String toString() {
		return "SysDict [dict_id=" + dict_id + ", dict_name=" + dict_name
				+ ", dict_type=" + dict_type + ", dict_remark=" + dict_remark
				+ "]";
	}

	public String getDict_name() {
        return dict_name;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name == null ? null : dict_name.trim();
    }

    public String getDict_type() {
        return dict_type;
    }

    public void setDict_type(String dict_type) {
        this.dict_type = dict_type == null ? null : dict_type.trim();
    }

    public String getDict_remark() {
        return dict_remark;
    }

    public void setDict_remark(String dict_remark) {
        this.dict_remark = dict_remark == null ? null : dict_remark.trim();
    }
}