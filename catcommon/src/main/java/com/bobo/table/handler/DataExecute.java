package com.bobo.table.handler;

public interface DataExecute<E> {
    /**
     * 执行处理多类别数据维度
     *
     * @param e
     */
    void handler(E e);

}
