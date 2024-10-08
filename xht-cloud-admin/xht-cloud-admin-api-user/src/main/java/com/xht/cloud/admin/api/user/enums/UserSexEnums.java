package com.xht.cloud.admin.api.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：用户性别
 *
 * @author : 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum UserSexEnums implements IEnum<Integer> {



    MALE(0), // 男

    FEMALE(1), // 女

    UNKNOWN(2), //未知
    ;

    @JsonValue
    @EnumValue
    private final Integer value;

}
