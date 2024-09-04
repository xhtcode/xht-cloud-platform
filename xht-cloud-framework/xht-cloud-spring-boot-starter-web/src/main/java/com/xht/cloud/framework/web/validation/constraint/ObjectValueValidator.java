package com.xht.cloud.framework.web.validation.constraint;

import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.web.validation.ObjectValueEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述 ：判断对象是否为空 如果是{@code String}或者{@code Collection}或者{@code Map} 可以判断长度
 *
 * @author : 小糊涂
 **/
public class ObjectValueValidator implements ConstraintValidator<ObjectValueEmpty, Object> {

    private int length;

    /**
     * 初始化方法
     *
     * @param constraintAnnotation {@link ObjectValueEmpty}
     */
    @Override
    public void initialize(ObjectValueEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.length = constraintAnnotation.length();
    }

    /**
     * 校验的业务逻辑
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (Objects.isNull(obj)) {
                return false;
            }
            if (obj instanceof Optional<?> optional) {
                obj = optional.orElseThrow(RuntimeException::new);
            }
            if (obj instanceof CharSequence charSequence) {
                validator(charSequence.length());
                return StringUtils.hasLength(charSequence) && charSequence.length() < this.length;
            }
            if (obj.getClass().isArray()) {
                validator(Array.getLength(obj));
                return Array.getLength(obj) == 0;
            }
            if (obj instanceof Collection<?> collection) {
                validator(collection.size());
                return collection.isEmpty();
            }
            if (obj instanceof Map<?, ?> map) {
                validator(map.size());
                return map.isEmpty();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validator(int sourceLength) {
        if (this.length >= 0 && sourceLength > this.length) {
            throw new RuntimeException();
        }
    }
}
