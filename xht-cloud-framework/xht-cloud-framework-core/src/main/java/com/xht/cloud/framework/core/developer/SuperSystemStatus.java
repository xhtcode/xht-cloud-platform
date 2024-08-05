package com.xht.cloud.framework.core.developer;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：系统 变量标识
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum SuperSystemStatus implements IEnum<Integer> {

    /**
     * 否
     */
    NO(0),

    /**
     * 是
     */
    YES(1);

    @JsonValue
    @EnumValue
    private final Integer value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SuperSystemStatus of(Integer value) {
        for (SuperSystemStatus item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}
