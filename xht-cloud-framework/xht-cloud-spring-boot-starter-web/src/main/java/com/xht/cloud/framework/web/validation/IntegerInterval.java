package com.xht.cloud.framework.web.validation;

import com.xht.cloud.framework.web.validation.constraint.IntegerIntervalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 描述 ：判断字段是否在min和max之间
 * 适用类型：{@code Integer} 、{@code int}
 *
 * @author 小糊涂
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IntegerIntervalValidator.class})
public @interface IntegerInterval {

    /**
     * 提示信息
     */
    String message() default "大小必须在{min}和{max}之间";

    /**
     * 最小值
     */
    int min() default 0;

    /**
     * 最大值
     */
    int max() default 999;

    /**
     * 分组
     */
    Class<?>[] groups() default {};

    /**
     * 在大多数情况下，该方法不需要自定义实现，
     * 因为它通常用于与验证器（Validator）库和验证约束（Constraint）相关的高级用例。
     */
    Class<? extends Payload>[] payload() default {};

}
