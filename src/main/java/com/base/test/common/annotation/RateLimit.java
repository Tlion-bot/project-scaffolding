package com.base.test.common.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于防刷限流的注解
 *      默认是5秒内只能调用一次
 * @author wujiangbo
 * @date 2022-08-23 18:36
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /** 限流的key */
    String key() default "limit:";

    /** 周期,单位是秒 */
    int cycle() default 1;

    /** 请求次数 */
    int count() default 5;

    /** 默认提示信息 */
    String msg() default "访问过于频繁请稍后再试";
}
