package com.xht.cloud.admin.api.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum OperationStatus implements IEnum<Integer> {

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
    public static OperationStatus of(Integer value) {
        for (OperationStatus item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }

}
