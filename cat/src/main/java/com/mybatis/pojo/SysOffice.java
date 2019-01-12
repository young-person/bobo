package com.mybatis.pojo;

import java.util.Date;

public class SysOffice {
    private String id;

    private String parent_id;

    private String parent_ids;

    private String name;

    private Long sort;

    private String area_id;

    private String code;

    private String type;

    private String grade;

    private String address;

    private String zip_code;

    private String master;

    private String phone;

    private String fax;

    private String email;

    private String USEABLE;

    private String PRIMARY_PERSON;

    private String DEPUTY_PERSON;

    private String create_by;

    private Date create_date;

    private String update_by;

    private Date update_date;

    private String remarks;

    private String del_flag;

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

    public String getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(String parent_ids) {
        this.parent_ids = parent_ids == null ? null : parent_ids.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id == null ? null : area_id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code == null ? null : zip_code.trim();
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master == null ? null : master.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUSEABLE() {
        return USEABLE;
    }

    public void setUSEABLE(String USEABLE) {
        this.USEABLE = USEABLE == null ? null : USEABLE.trim();
    }

    public String getPRIMARY_PERSON() {
        return PRIMARY_PERSON;
    }

    public void setPRIMARY_PERSON(String PRIMARY_PERSON) {
        this.PRIMARY_PERSON = PRIMARY_PERSON == null ? null : PRIMARY_PERSON.trim();
    }

    public String getDEPUTY_PERSON() {
        return DEPUTY_PERSON;
    }

    public void setDEPUTY_PERSON(String DEPUTY_PERSON) {
        this.DEPUTY_PERSON = DEPUTY_PERSON == null ? null : DEPUTY_PERSON.trim();
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
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
}