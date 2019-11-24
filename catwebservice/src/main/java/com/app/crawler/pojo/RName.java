package com.app.crawler.pojo;

import java.lang.annotation.*;

/**
 * @Description: TODO
 * @date 2019年11月12日 下午10:41:04 
 * @ClassName: RName 字段注解
 */
@Documented
@Target({ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RName {
    String value() default "";
}
