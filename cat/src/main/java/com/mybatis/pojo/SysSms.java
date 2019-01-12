package com.mybatis.pojo;

import java.util.Date;

public class SysSms {
    private String id;

    private String mobile;

    private String msg;

    private String type;

    @Override
	public String toString() {
		return "SysSms [id=" + id + ", mobile=" + mobile + ", msg=" + msg
				+ ", type=" + type + ", expired_date=" + expired_date
				+ ", create_date=" + create_date + ", create_by=" + create_by
				+ ", update_date=" + update_date + ", update_by=" + update_by
				+ ", del_flag=" + del_flag + ", is_received=" + is_received
				+ ", sync_return_result=" + sync_return_result + ", code="
				+ code + "]";
	}

	private Date expired_date;

    private Date create_date;

    private String create_by;

    private Date update_date;

    private String update_by;

    private String del_flag;

    private String is_received;

    private String sync_return_result;

    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(Date expired_date) {
        this.expired_date = expired_date;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag == null ? null : del_flag.trim();
    }

    public String getIs_received() {
        return is_received;
    }

    public void setIs_received(String is_received) {
        this.is_received = is_received == null ? null : is_received.trim();
    }

    public String getSync_return_result() {
        return sync_return_result;
    }

    public void setSync_return_result(String sync_return_result) {
        this.sync_return_result = sync_return_result == null ? null : sync_return_result.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}