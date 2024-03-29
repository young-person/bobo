package com.app.distributedlock.manager;

/** 分布式锁统一管理操作接口
 */
public interface CLockManager {

    /**
     * 加锁并执行业务
     * @param lockKey
     * @param callBack
     */
    void callBack(String lockKey, LockCallBack callBack);

    /**
     * 加锁并返回执行业务的返回值
     * @param lockKey
     * @param callBack
     * @param <T>
     * @return
     */
    <T> T callBack(String lockKey, ReturnCallBack<T> callBack);
}
