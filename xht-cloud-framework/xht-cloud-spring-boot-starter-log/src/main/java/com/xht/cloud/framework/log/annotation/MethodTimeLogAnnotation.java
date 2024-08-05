package com.xht.cloud.framework.log.annotation;


import java.lang.annotation.*;

/**
 * 描述 ：aop 方法计时 日志
 *
 * @author 小糊涂
 **/
@Documented
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodTimeLogAnnotation {

    /**
     * 接口名
     */
    String value() default "";

}
