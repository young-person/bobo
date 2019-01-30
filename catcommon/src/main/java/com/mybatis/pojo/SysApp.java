package com.mybatis.pojo;

import java.util.Date;

public class SysApp extends SysAppKey {
    private String aliasname;

    private String urlFunctionName;

    private String urlName;

    private Date createtime;

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname == null ? null : aliasname.trim();
    }

    public String getUrlFunctionName() {
        return urlFunctionName;
    }

    public void setUrlFunctionName(String urlFunctionName) {
        this.urlFunctionName = urlFunctionName == null ? null : urlFunctionName.trim();
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName == null ? null : urlName.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}