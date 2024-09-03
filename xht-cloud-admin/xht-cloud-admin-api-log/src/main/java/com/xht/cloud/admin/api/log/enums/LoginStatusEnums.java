package com.xht.cloud.admin.api.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：登录状态  0登录成功 1登录失败
 *
 * @author : 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum LoginStatusEnums implements IEnum<String> {

    /**
     * 0登录成功
     */
    SUCCESS("0"),

    /**
     * 1登录失败
     */
    ERROR("1"),

    /**
     * 退出登录
     */
    LOGIN_OUT("2");

    @JsonValue
    @EnumValue
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LoginStatusEnums of(String value) {
        for (LoginStatusEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}