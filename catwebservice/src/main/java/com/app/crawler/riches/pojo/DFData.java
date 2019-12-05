package com.app.crawler.riches.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 东方财富网日数据
 */
public class DFData {

    //http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?rtntype=5&cb=jQuery11240405187617414106_1575556914407&id=%s&type=t
    private String name;
    private String code;
    private DFInfo info;
    private List<String> data = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DFInfo getInfo() {
        return info;
    }

    public void setInfo(DFInfo info) {
        this.info = info;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
