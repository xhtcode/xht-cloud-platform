package com.xht.cloud.framework.jackson.desensitization.annotation;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.cloud.framework.jackson.desensitization.jackson.SensitiveFieldJsonSerializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述 ：自定义脱敏注解
 *
 * @author 小糊涂
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveFieldJsonSerializable.class)
public @interface SensitiveField {

    /**
     * @return 脱敏类型
     */
    String type();

}
