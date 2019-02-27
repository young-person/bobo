
package com.bobo.enums;


public enum EventTypeEnum {

    SAVE(0, "保存"),

    DELETE(1, "删除"),

    UPDATE_STATUS(2, "更新状态"),


    UPDATE_PARTICIPANT(3, "更新参与者"),

    UPDATE_FAIR(4, "更新错误信息");



    private int code;

    private String desc;

    EventTypeEnum(final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }
}
