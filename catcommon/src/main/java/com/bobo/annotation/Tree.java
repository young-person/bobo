package com.bobo.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {
    String id() default "";
    String pid() default "";
    String text() default "";


    /**
     * 针对菜单栏进行扩展
     * @return
     */
    String icon() default "";
    String url() default  "";

}
