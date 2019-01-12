package com.mybatis.pojo;

public class SysFileUpload {
    private Integer id;

    private String name;

    private String path;

    private String factpath;

    private String ext;

    private String originalname;

    private Integer type;

    private Integer size;

    @Override
	public String toString() {
		return "SysFileUpload [id=" + id + ", name=" + name + ", path=" + path
				+ ", factpath=" + factpath + ", ext=" + ext + ", originalname="
				+ originalname + ", type=" + type + ", size=" + size
				+ ", remark=" + remark + ", business_type=" + business_type
				+ ", update_time=" + update_time + ", update_id=" + update_id
				+ ", create_time=" + create_time + ", create_id=" + create_id
				+ "]";
	}

	private String remark;

    private Integer business_type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getFactpath() {
        return factpath;
    }

    public void setFactpath(String factpath) {
        this.factpath = factpath == null ? null : factpath.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname == null ? null : originalname.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(Integer business_type) {
        this.business_type = business_type;
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