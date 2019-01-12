package com.mybatis.pojo;

import java.util.Date;

public class SysElement extends SysElementKey {
    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}