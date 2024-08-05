package com.xht.cloud.admin.api.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：用户状态
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum UserStatusEnums implements IEnum<Integer> {

    NORMAL(0),//正常

    USER_LOCK(1),//锁定

    UN_ACTIVATED(2),//未激活

    USER_BLACK(3),//黑名单

    USER_EXPIRED(4),//账号过期

    PASSWORD_EXPIRED(5),//密码过期

    ;
    @JsonValue
    @EnumValue
    private final Integer value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserStatusEnums of(Integer code) {
        for (UserStatusEnums value : values()) {
            if (value.getValue().equals(code)) return value;
        }
        return null;
    }
}
