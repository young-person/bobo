package com.mybatis.pojo;

public class SysLog {
    private Integer id;

    private Integer log_type;

    private String oper_object;

    private String oper_table;

    private Integer oper_id;

    @Override
	public String toString() {
		return "SysLog [id=" + id + ", log_type=" + log_type + ", oper_object="
				+ oper_object + ", oper_table=" + oper_table + ", oper_id="
				+ oper_id + ", oper_type=" + oper_type + ", oper_remark="
				+ oper_remark + ", create_time=" + create_time + ", create_id="
				+ create_id + "]";
	}

	private String oper_type;

    private String oper_remark;

    private String create_time;

    private Integer create_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLog_type() {
        return log_type;
    }

    public void setLog_type(Integer log_type) {
        this.log_type = log_type;
    }

    public String getOper_object() {
        return oper_object;
    }

    public void setOper_object(String oper_object) {
        this.oper_object = oper_object == null ? null : oper_object.trim();
    }

    public String getOper_table() {
        return oper_table;
    }

    public void setOper_table(String oper_table) {
        this.oper_table = oper_table == null ? null : oper_table.trim();
    }

    public Integer getOper_id() {
        return oper_id;
    }

    public void setOper_id(Integer oper_id) {
        this.oper_id = oper_id;
    }

    public String getOper_type() {
        return oper_type;
    }

    public void setOper_type(String oper_type) {
        this.oper_type = oper_type == null ? null : oper_type.trim();
    }

    public String getOper_remark() {
        return oper_remark;
    }

    public void setOper_remark(String oper_remark) {
        this.oper_remark = oper_remark == null ? null : oper_remark.trim();
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