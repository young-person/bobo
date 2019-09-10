package com.bobo.table.handler;

public abstract class Actuator<T> {

    /**
     * sql 执行  获取唯独 代码信息数据
     * @param sql
     * @return
     */
    public abstract T exec(String sql);

}
