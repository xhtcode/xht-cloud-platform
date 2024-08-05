package com.xht.cloud.framework.web.desensitization;

import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.web.desensitization.convert.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ： 自定义json序列化
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DesensitizationStrategyEnum implements IEnum<DesensitizationConvert> {

    /**
     *手机号
     */
    PHONE("手机号", new PhoneDesensitizationConvert()),
    /**
     *固定电话
     */
    FIXED_PHONE("固定电话", new FixedPhoneBankCardDesensitizationConvert()),
    /**
     *邮箱
     */
    EMAIL("邮箱", new EmailDesensitizationConvert()),
    /**
     *身份证号
     */
    ID_CARD("身份证号", new IdCardDesensitizationConvert()),
    /**
     *地址
     */
    ADDRESS("地址", new AddressDesensitizationConvert()),
    /**
     *银行卡号
     */
    BANK_CARD("银行卡号", new BankCardDesensitizationConvert()),
    /**
     *中文姓名脱敏
     */
    NAME("姓名脱敏", new NameDesensitizationConvert()),
    /**
     *密码脱敏
     */
    PASSWORD("密码脱敏", new PasswordDesensitizationConvert());


    private final String name;

    private final DesensitizationConvert value;

}



