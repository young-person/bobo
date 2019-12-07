package com.app.crawler.riches.pojo;

import com.bobo.base.CatException;

import java.util.*;

/**
 * 东方财富网日数据
 */
public class DFData {

    private String name;
    private String code;
    private DFInfo info;
    private List<String> data = new ArrayList<>();

    /**
     * 获取开始时间
     */
    private String start;

    /**
     * 获取结束时间
     */
    private String end;

    public String getStart() {
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] s1 = o1.split(",");
                String[] s2 = o2.split(",");
                String s = s1[0].replace("-", "");
                String e = s2[0].replace("-", "");
                return Integer.valueOf(s) - Integer.valueOf(e);
            }
        });
        start = data.get(0).split(",")[0].replace("-", "");
        if (data.size() == 1){
            end = start;
        }else if(data.size() == 0){
         start = "1990";
         end = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        }else{
            end = data.get(data.size() - 1).split(",")[0].replace("-", "");
        }
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        if (Objects.isNull(end)){
            throw new CatException("结束时间不能为null");
        }
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

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
