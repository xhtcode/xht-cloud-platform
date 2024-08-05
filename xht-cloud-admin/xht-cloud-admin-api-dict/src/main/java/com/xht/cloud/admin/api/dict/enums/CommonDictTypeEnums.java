package com.xht.cloud.admin.api.dict.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：常用的字典类型
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum CommonDictTypeEnums implements IEnum<String> {

    ;

    @JsonValue
    @EnumValue
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DictStatusEnums of(String code) {
        return null;
    }
}
