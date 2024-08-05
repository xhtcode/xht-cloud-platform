package com.xht.cloud.framework.redis.limit.annotation;

import java.lang.annotation.*;

/**
 * 描述 ：接口限流 默认一分钟10次
 *
 * @author 小糊涂
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RequestLimit {

    /**
     * 资源的key,唯一
     * 作用：不同的接口，不同的流量控制
     */
    String key();

    /**
     * 最多的访问限制次数
     */
    long limit() default 10 * 1000;

    /**
     * 过期时间也可以理解为单位时间，单位秒，默认60
     */
    long expire() default 60;


    /**
     * 得不到令牌的提示语
     */
    String msg() default "系统繁忙,请稍后再试";
}
