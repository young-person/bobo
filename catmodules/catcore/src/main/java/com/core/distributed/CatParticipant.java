package com.core.distributed;

import java.io.Serializable;

/**
 * 事务参与者实例
 */
public class CatParticipant implements Serializable {
    private static final long serialVersionUID = -2590970715288987627L;

    /**
     * 事务唯一ID
     */
    private String transId;

    /**
     * 队列(TOPIC,如果是rocketmq或者aliyunmq,这里包含TOPIC和TAG),用,区分.
     */
    private String destination;

    /**
     * 消息模式
     */
    private Integer pattern;

    /**
     * 执行器
     */
    private CatInvocation invocation;

    public CatParticipant(String transId, String destination, Integer pattern, CatInvocation invocation) {
        this.transId = transId;
        this.destination = destination;
        this.pattern = pattern;
        this.invocation = invocation;
    }

    @Override
    public String toString() {
        return "CatParticipant{" +
                "transId='" + transId + '\'' +
                ", destination='" + destination + '\'' +
                ", pattern=" + pattern +
                ", invocation=" + invocation +
                '}';
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }

    public CatInvocation getInvocation() {
        return invocation;
    }

    public void setInvocation(CatInvocation invocation) {
        this.invocation = invocation;
    }
}
