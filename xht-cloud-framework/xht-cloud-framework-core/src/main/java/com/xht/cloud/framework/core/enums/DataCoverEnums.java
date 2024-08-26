package com.xht.cloud.framework.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：数据覆盖
 *
 * @author : 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DataCoverEnums implements IEnum<Integer> {


    NO(0),//存在,

    YES(1);//不存在,

    @JsonValue
    @EnumValue
    private final Integer value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DataCoverEnums of(Integer value) {
        for (DataCoverEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}
