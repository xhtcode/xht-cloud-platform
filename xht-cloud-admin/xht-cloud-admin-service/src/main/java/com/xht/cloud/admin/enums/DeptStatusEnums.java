package com.xht.cloud.admin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：部门状态
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DeptStatusEnums implements IEnum<Integer> {

    NORMAL(0),//正常

    DEPT_LOCK(1),//锁定

    DEPT_STOP(2),//停用

    DEPT_BLACK(3),//黑名单

    ;
    @JsonValue
    @EnumValue
    private final Integer value;

    public static DeptStatusEnums of(Integer code) {
        for (DeptStatusEnums value : values()) {
            if (value.getValue().equals(code)) return value;
        }
        return null;
    }


}
