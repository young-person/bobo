package com.app.distributedlock.manager;

/** 有返回值的业务执行接口
 */
public interface ReturnCallBack<T> {
    T execute();
}
