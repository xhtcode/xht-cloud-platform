package com.xht.cloud.framework.security.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum SkipAuthTypeEnums implements IEnum<String> {

    INNER("内部调用"),

    OUTER("外部调用");

    @JsonValue
    private final String value;

}
