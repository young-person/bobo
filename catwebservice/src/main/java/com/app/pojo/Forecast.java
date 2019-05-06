package com.app.pojo;

import java.io.Serializable;

public class Forecast implements Serializable {

    private static final long serialVersionUID = -2657364199493193554L;

    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static void main(String[] args) {
        String filename = "1556093452760545.jpg";
        //文件名
        String fileName = filename.substring(0, filename.lastIndexOf("."));
        //文件扩展名
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        System.out.printf(ext);
    }
}
