package com.xht.cloud.framework.web.validation.constraint;

import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 描述 ：判断字段是否在min和max之间
 * 适用类型：{@code Integer} 、{@code int}
 *
 * @author 小糊涂
 **/
public class IntegerIntervalValidator implements ConstraintValidator<IntegerInterval, Integer> {

    /**
     * 最小值
     */
    private int min;

    /**
     * 最大值
     */
    private int max;

    /**
     * 初始化方法
     *
     * @param constraintAnnotation {@link IntegerInterval}
     */
    @Override
    public void initialize(IntegerInterval constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        validateParameters();
    }

    /**
     * 校验方法
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (min <= value) && (max >= value);
    }

    private void validateParameters() {
        if (max <= min) {
            throw new BizException("【编码异常】 min 应该比 max 小");
        }
    }
}
