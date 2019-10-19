package com.mybatis.pojo;

public class SysDictDetail {
    private Integer detail_id;

    private String dict_type;

    private String detail_name;

    private String detail_code;

    private String detail_sort;

    private String detail_type;

    private String detail_state;

    private String detail_content;

    @Override
	public String toString() {
		return "SysDictDetail [detail_id=" + detail_id + ", dict_type="
				+ dict_type + ", detail_name=" + detail_name + ", detail_code="
				+ detail_code + ", detail_sort=" + detail_sort
				+ ", detail_type=" + detail_type + ", detail_state="
				+ detail_state + ", detail_content=" + detail_content
				+ ", detail_remark=" + detail_remark + ", create_time="
				+ create_time + ", create_id=" + create_id + "]";
	}

	private String detail_remark;

    private String create_time;

    private Integer create_id;

    public Integer getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public String getDict_type() {
        return dict_type;
    }

    public void setDict_type(String dict_type) {
        this.dict_type = dict_type == null ? null : dict_type.trim();
    }

    public String getDetail_name() {
        return detail_name;
    }

    public void setDetail_name(String detail_name) {
        this.detail_name = detail_name == null ? null : detail_name.trim();
    }

    public String getDetail_code() {
        return detail_code;
    }

    public void setDetail_code(String detail_code) {
        this.detail_code = detail_code == null ? null : detail_code.trim();
    }

    public String getDetail_sort() {
        return detail_sort;
    }

    public void setDetail_sort(String detail_sort) {
        this.detail_sort = detail_sort == null ? null : detail_sort.trim();
    }

    public String getDetail_type() {
        return detail_type;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type == null ? null : detail_type.trim();
    }

    public String getDetail_state() {
        return detail_state;
    }

    public void setDetail_state(String detail_state) {
        this.detail_state = detail_state == null ? null : detail_state.trim();
    }

    public String getDetail_content() {
        return detail_content;
    }

    public void setDetail_content(String detail_content) {
        this.detail_content = detail_content == null ? null : detail_content.trim();
    }

    public String getDetail_remark() {
        return detail_remark;
    }

    public void setDetail_remark(String detail_remark) {
        this.detail_remark = detail_remark == null ? null : detail_remark.trim();
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