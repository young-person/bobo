package com.mybatis.pojo;

public class SysRoleOfficeKey {
    private String role_id;

    private String office_id;

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id == null ? null : role_id.trim();
    }

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id == null ? null : office_id.trim();
    }
}