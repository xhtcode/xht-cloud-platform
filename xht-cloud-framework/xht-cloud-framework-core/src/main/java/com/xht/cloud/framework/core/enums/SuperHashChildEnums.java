package com.xht.cloud.framework.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：是否存在下级
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum SuperHashChildEnums implements IEnum<String> {

    NO("0"),//存在,

    YES("1");//不存在,

    @EnumValue
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SuperHashChildEnums of(String value) {
        for (SuperHashChildEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}
