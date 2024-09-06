package com.xht.cloud.framework.jackson.translation.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.cloud.framework.jackson.translation.databind.IEnumJsonDeserializer;
import com.xht.cloud.framework.jackson.translation.databind.IEnumJsonSerializer;
import com.xht.cloud.framework.jackson.translation.constant.TransConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述 ：枚举序列化翻译只能用于 {@link com.xht.cloud.framework.core.enums.IEnum}
 *
 * @author : 小糊涂
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = IEnumJsonSerializer.class, nullsUsing = IEnumJsonSerializer.class)
@JsonDeserialize(using = IEnumJsonDeserializer.class)
public @interface TransEnum {

    /**
     * 拼接的字段后缀
     *
     * @return 后缀
     */
    String suffix() default TransConstant.FIELD_SUFFIX;

}
