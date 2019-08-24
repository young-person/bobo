package com.app.distributedlock.lock;


import com.app.distributedlock.properties.LockProperties;

/** 加锁、释放锁操作接口
 */
public interface CLock {

    /**
     *
     * @param lock
     * @param lockProperties
     */
    /**
     * 获取锁
     * @param lock
     * @param lockProperties
     * @return redisLockKey
     */
    String lock(String lock, LockProperties lockProperties);



    /**
     * 获取锁
     * @param lock
     * @param lockProperties
     * @return redisLockKey
     */
    String unlock(String lock, LockProperties lockProperties);
}
