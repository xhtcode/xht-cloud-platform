package com.xht.cloud.framework.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：删除标识
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DelFlagEnum implements IEnum<String> {

    DELETE("0", "已删除"),

    NORMAL("1", "正常");

    @JsonValue
    @EnumValue
    private final String value;

    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DelFlagEnum of(String code) {
        return switch (code) {
            case "0" -> DELETE;
            case "1" -> NORMAL;
            default -> null;
        };
    }

}
