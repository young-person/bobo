package com.bobo.domain;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = -1220101004792874251L;
    private PageBean page;
    private List<T> dataList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
