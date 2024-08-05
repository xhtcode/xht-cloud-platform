package com.xht.cloud.admin.api.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 超级管理员枚举.
 *
 * @author 小糊涂
 */
@Getter
@AllArgsConstructor
public enum SuperAdminUserEnums implements IEnum<Integer> {

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

    public Boolean isAdmin() {
        return Objects.equals(this.getValue(), YES.value);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SuperAdminUserEnums of(Integer value) {
        for (SuperAdminUserEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}
