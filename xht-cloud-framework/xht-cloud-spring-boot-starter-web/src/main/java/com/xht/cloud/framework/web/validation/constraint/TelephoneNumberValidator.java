package com.xht.cloud.framework.web.validation.constraint;

import com.xht.cloud.framework.web.validation.TelephoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * 描述 ：手机号校验器
 *
 * @author 小糊涂
 **/
public class TelephoneNumberValidator implements ConstraintValidator<TelephoneNumber, String> {

    private static final String REGEX_TEL = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";

    /**
     * 初始化方法
     *
     * @param constraintAnnotation {@link TelephoneNumber}
     */
    @Override
    public void initialize(TelephoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * 校验的业务逻辑
     */
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return Pattern.matches(REGEX_TEL, phone);
        } catch (Exception e) {
            return false;
        }
    }
}
