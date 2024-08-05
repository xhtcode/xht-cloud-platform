package com.xht.cloud.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：循环更新
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum SeqLoopEnum implements IEnum<String> {

    NO("0"),//不循环更新,

    YES("1");//循环更新,

    @EnumValue
    @JsonValue    //标记响应json值
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SeqLoopEnum of(String value) {
        for (SeqLoopEnum resetFlagEnum : values()) {
            if (resetFlagEnum.getValue().equals(value)) {
                return resetFlagEnum;
            }
        }
        return null;
    }
}
