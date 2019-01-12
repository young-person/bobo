package com.mybatis.pojo;

import java.util.Date;

public class SysArea {
    private String id;

    private String parent_id;

    private String code;

    private String name;

    private String simple_name;

    private String zip_code;

    private String area_number;

    private Byte level;

    private String path_ids;

    private String path_names;

    private String remarks;

    private String del_flag;

    private Date create_date;

    private String create_by;

    private Date update_date;

    private String update_by;

    private String parent_ids;

    private String type;

    private Integer sort;

    private String shipping_group;

    private String store_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id == null ? null : parent_id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSimple_name() {
        return simple_name;
    }

    public void setSimple_name(String simple_name) {
        this.simple_name = simple_name == null ? null : simple_name.trim();
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code == null ? null : zip_code.trim();
    }

    public String getArea_number() {
        return area_number;
    }

    public void setArea_number(String area_number) {
        this.area_number = area_number == null ? null : area_number.trim();
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getPath_ids() {
        return path_ids;
    }

    public void setPath_ids(String path_ids) {
        this.path_ids = path_ids == null ? null : path_ids.trim();
    }

    public String getPath_names() {
        return path_names;
    }

    public void setPath_names(String path_names) {
        this.path_names = path_names == null ? null : path_names.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag == null ? null : del_flag.trim();
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

    public String getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(String parent_ids) {
        this.parent_ids = parent_ids == null ? null : parent_ids.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getShipping_group() {
        return shipping_group;
    }

    public void setShipping_group(String shipping_group) {
        this.shipping_group = shipping_group == null ? null : shipping_group.trim();
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id == null ? null : store_id.trim();
    }
}