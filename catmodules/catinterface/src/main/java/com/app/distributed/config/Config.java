package com.app.distributed.config;

import java.io.Serializable;

/**
 * 基础数据库配置
 */
public class Config implements Serializable {

    private static final long serialVersionUID = -6834099969596119656L;
    /**
     * 用户名.
     */
    protected String username;
    /**
     * 密码.
     */
    protected String password;
    /**
     * 驱动名
     */
    protected String driverClass;
    /**
     * url连接
     */
    protected String url;

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

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
