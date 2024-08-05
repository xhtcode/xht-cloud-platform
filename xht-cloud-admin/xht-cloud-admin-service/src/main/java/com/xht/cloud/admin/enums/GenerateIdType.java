package com.xht.cloud.admin.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 描述 ：id类型
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum GenerateIdType implements IEnum<Integer> {

    UUID(1001, "UUID"),

    SNOWFLAKE(1002, "SnowFlake"),

    SEQUENCE(1003, "自定义序列");

    private final Integer value;

    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GenerateIdType generateIdType(Integer value) {
        if (Objects.isNull(value)) return UUID;
        for (GenerateIdType generateIdType : values()) {
            if (Objects.equals(generateIdType.getValue(), value)) {
                return generateIdType;
            }
        }
        return UUID;
    }

}
