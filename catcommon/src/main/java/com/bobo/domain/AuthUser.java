package com.bobo.domain;

import java.io.Serializable;
import java.util.Objects;

public class AuthUser implements Serializable {
    private static final long serialVersionUID = 8116817810829835862L;
    private String username;
    private String password;

    private int userid;//唯一主键
    private String backUrl;//当前访问应用ID


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUser)) return false;
        AuthUser user = (AuthUser) o;
        return userid == user.userid &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(backUrl, user.backUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, userid, backUrl);
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userid=" + userid +
                ", backUrl='" + backUrl + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
}
