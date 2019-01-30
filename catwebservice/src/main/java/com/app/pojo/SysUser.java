package com.app.pojo;

public class SysUser {
    private Integer userid;

    private String username;

    private String password;

    private String realname;

    private Integer departid;

    private Integer usertype;

    private Integer state;

    private String thirdid;

    private String endtime;

    private String email;

    private String tel;

    private String address;

    private String title_url;

    private String remark;

    private String theme;

    private Integer back_site_id;

    private Integer create_site_id;

    private String create_time;

    private Integer create_id;

    private String birthday;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getThirdid() {
        return thirdid;
    }

    public void setThirdid(String thirdid) {
        this.thirdid = thirdid == null ? null : thirdid.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTitle_url() {
        return title_url;
    }

    public void setTitle_url(String title_url) {
        this.title_url = title_url == null ? null : title_url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public Integer getBack_site_id() {
        return back_site_id;
    }

    public void setBack_site_id(Integer back_site_id) {
        this.back_site_id = back_site_id;
    }

    public Integer getCreate_site_id() {
        return create_site_id;
    }

    public void setCreate_site_id(Integer create_site_id) {
        this.create_site_id = create_site_id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }
}