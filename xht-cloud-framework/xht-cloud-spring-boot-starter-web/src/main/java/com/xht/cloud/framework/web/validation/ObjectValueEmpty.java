package com.xht.cloud.framework.web.validation;

import com.xht.cloud.framework.web.validation.constraint.TelephoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 描述 ：校验对象是否为空，并且判断长度是否够
 *
 * @author : 小糊涂
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TelephoneNumberValidator.class})
public @interface ObjectValueEmpty {

    /**
     * 提示信息
     */
    String message() default "对象不能为空！";

    /**
     * 分组
     */
    Class<?>[] groups() default {};

    /**
     * 可以判断字符串长度、集合长度、数组长度 必须大于等于0时才可以生效
     *
     * @return 长度
     */
    int length() default -1;

    /**
     * 在大多数情况下，该方法不需要自定义实现，
     * 因为它通常用于与验证器（Validator）库和验证约束（Constraint）相关的高级用例。
     */
    Class<? extends Payload>[] payload() default {};

}
