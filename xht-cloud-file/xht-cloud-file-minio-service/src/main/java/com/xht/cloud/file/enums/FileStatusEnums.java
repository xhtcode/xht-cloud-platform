package com.xht.cloud.file.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：文件状态
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum FileStatusEnums implements IEnum<Integer> {

    SUCCESS(0, "正常"),

    SHARDING_CENTER(1, "分片中"),

    ;

    @JsonValue
    @EnumValue
    private final Integer value;


    private final String desc;


}
