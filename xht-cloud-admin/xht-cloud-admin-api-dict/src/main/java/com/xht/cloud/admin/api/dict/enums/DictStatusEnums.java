package com.xht.cloud.admin.api.dict.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ： 字典状态枚举
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DictStatusEnums implements IEnum<String> {

    ALL("-1", "全部"),

    DISABLE("0", "已删除"),

    NORMAL("1", "正常"),

    ;

    @JsonValue
    @EnumValue
    private final String value;

    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DictStatusEnums of(String code) {
        return switch (code) {
            case "0" -> DISABLE;
            case "1" -> NORMAL;
            default -> null;
        };
    }
}
