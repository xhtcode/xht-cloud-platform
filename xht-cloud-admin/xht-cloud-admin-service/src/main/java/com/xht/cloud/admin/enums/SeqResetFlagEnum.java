package com.xht.cloud.admin.enums;

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
public enum SeqResetFlagEnum implements IEnum<String> {
    NO_RESET("0"),//不重置
    DAY("D"),//每天
    MONTH("M"),//月
    YEAR("Y");//年

    @EnumValue
    @JsonValue    //标记响应json值
    private final String value;


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SeqResetFlagEnum of(String value) {
        for (SeqResetFlagEnum resetFlagEnum : values()) {
            if (resetFlagEnum.getValue().equals(value)) {
                return resetFlagEnum;
            }
        }
        return null;
    }

}
