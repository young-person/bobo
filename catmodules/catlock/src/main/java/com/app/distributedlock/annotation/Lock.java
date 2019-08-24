package com.app.distributedlock.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lock {

    /**
     * 锁的KEY
     * @return
     */
    String key() default "";

    /**
     * 锁的前缀
     * @return
     */
    String lockPre() default "";

    /**
     * 重试次数
     * @return
     */
    int retryCount() default 0;

    /**
     * 持锁时间 单位：s
     * @return
     */
    int expiredTime() default 0;


}
