package com.xht.cloud.framework.file.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：资源类型
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum ResourceType implements IEnum<String> {

    STATIC_RESOURCE("0", "静态资源"),

    SERVER_RESOURCE("1", "业务资源"),

    PUBLIC_RESOURCE("2", "公共资源"),

    ;

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;


}
