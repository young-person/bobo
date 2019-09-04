package com.bobo.table.handler;

/**
 * 进行条件数据解析
 * @param <T>
 */
public interface ParseBase<T> {
    /**
     * 解析条件参数 实体bean
     * @param condition
     */
    void parse(T condition);
}
