package com.xht.cloud.framework.security.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 超级管理员角色枚举.
 *
 * @author 小糊涂
 */
@Getter
@AllArgsConstructor
public enum SuperClientAutoApproveEnums implements IEnum<String> {

    /**
     * 否
     */
    NO("0"),

    /**
     * 是
     */
    YES("1");

    @JsonValue
    private final String value;

    public static SuperClientAutoApproveEnums of(String value) {
        return Objects.equals(YES.getValue(), value) ? YES : NO;
    }

    public Boolean isValue() {
        return Objects.equals(YES.getValue(), this.getValue());
    }
}
