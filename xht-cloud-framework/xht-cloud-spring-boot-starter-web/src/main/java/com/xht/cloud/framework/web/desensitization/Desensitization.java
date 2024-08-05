package com.xht.cloud.framework.web.desensitization;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.cloud.framework.web.desensitization.jackson.DesensitizationJsonSerializable;

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
@JsonSerialize(using = DesensitizationJsonSerializable.class)
public @interface Desensitization {

    /**
     * @return 脱敏策略
     */
    DesensitizationStrategyEnum value();

}
