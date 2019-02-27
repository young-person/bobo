package com.bobo.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 分布式事物 常用枚举 属性
 */
public enum JTAEnum {
    /**
     * 事务唯一名
     */
    TRANSACTION(0,"TRANSACTION"),


    START(1, "发起者"),

    LOCAL(2, "本地执行"),

    PROVIDER(3, "提供者"),

    /**
     * 事件类型定义
     */
    SAVE(10, "保存"),

    DELETE(11, "删除"),

    UPDATE_STATUS(12, "更新状态"),

    UPDATE_PARTICIPANT(13, "更新参与者"),

    UPDATE_FAIR(14, "更新错误信息"),

    /**
     * 事务状态
     */
    ROLLBACK(20, "回滚"),

    COMMIT(21, "已经提交"),

    BEGIN(32, "开始"),

    SEND_MSG(33, "可以发送消息"),

    FAILURE(34, "失败"),

    PRE_COMMIT(35, "预提交"),

    LOCK(36, "锁定");




    private int code;

    private String desc;

    JTAEnum(final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static JTAEnum acquireByCode(final int code) {
        Optional<JTAEnum> tccRoleEnum =
                Arrays.stream(JTAEnum.values())
                        .filter(v -> Objects.equals(v.getCode(), code))
                        .findFirst();
        return tccRoleEnum.orElse(JTAEnum.START);

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
