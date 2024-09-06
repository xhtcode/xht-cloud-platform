package com.xht.cloud.framework.jackson.translation.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.cloud.framework.jackson.translation.constant.TransConstant;
import com.xht.cloud.framework.jackson.translation.databind.TransDictJsonSerializer;
import com.xht.cloud.framework.jackson.translation.enums.TransDictEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述 ：翻译
 *
 * @author : 小糊涂
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = TransDictJsonSerializer.class, nullsUsing = TransDictJsonSerializer.class)
public @interface TransDict {

    /**
     * @return 字典翻译类型
     */
    TransDictEnum type() default TransDictEnum.DICT_ID;

    /**
     * 字典类型
     */
    String dictType() default "";

    /**
     * 默认字段名称
     * {@code fieldName} 和 {@code fieldNameSuffix} 同时存在时选取{@code fieldName}
     */
    String fieldName() default "";

    /**
     * 拼接的字段后缀
     * {@code fieldName} 和 {@code fieldNameSuffix} 同时存在时选取{@code fieldName}
     *
     * @return 后缀
     */
    String fieldNameSuffix() default TransConstant.FIELD_SUFFIX;
}
