package com.mybatis.pojo;

import com.bobo.annotation.Tree;

public class SysMenu {
    @Tree(id="id")
    private Integer id;
    @Tree(pid="pid")
    private Integer parentid;
    @Tree(text="name")
    private String name;

    private String urlkey;
    @Tree(url="url")
    private String url;

    private Integer status;

    private Integer type;

    private Integer sort;
    @Tree(icon = "icon")
    private String icon;

    private String create_time;

    private Integer create_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrlkey() {
        return urlkey;
    }

    public void setUrlkey(String urlkey) {
        this.urlkey = urlkey == null ? null : urlkey.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}