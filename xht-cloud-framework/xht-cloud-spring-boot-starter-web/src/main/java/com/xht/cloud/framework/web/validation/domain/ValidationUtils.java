package com.xht.cloud.framework.web.validation.domain;

import com.xht.cloud.framework.web.validation.ValidationExceptionBo;
import com.xht.cloud.framework.web.validation.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.function.Consumer;

/**
 * 描述 ：jsr303 异常校验
 *
 * @author : 小糊涂
 **/
public final class ValidationUtils {

    /**
     * 校验对象
     *
     * @param object 校验对象
     * @param groups 分组
     */
    public static <T> void validate(T object, Class<?>... groups) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        for (ConstraintViolation<T> violation : validate) {
            throw new ValidationException(String.valueOf(violation.getPropertyPath()), violation.getMessage());
        }
    }


    /**
     * 校验对象
     *
     * @param object 校验对象
     * @param groups 分组
     */
    public static <T> void validate(T object, Consumer<ValidationExceptionBo> consumer, Class<?>... groups) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        for (ConstraintViolation<T> violation : validate) {
            consumer.accept(new ValidationExceptionBo(String.valueOf(violation.getPropertyPath()), violation.getMessage()));
        }
    }
}
