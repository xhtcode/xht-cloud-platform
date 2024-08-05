package com.xht.cloud.admin.api.user.enums;

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
public enum UserTypeEnums implements IEnum<Integer> {

    ADMIN(0, "管理员"),

    SYSTEM_USER(1, "系统用户"),

    USER(2, "用户"),

    SHOP_USER(3, "商家");

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserTypeEnums of(Integer value) {
        for (UserTypeEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }

    @Override
    public String toString() {
        return "用户类型：{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
