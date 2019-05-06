package com.bobo.table.handler;

public abstract class Actuator<T> {

    /**
     * sql 执行
     * @param sql
     * @return
     */
    public abstract T exec(String sql);

}
