package com.xht.cloud.admin.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 描述 ：菜单类型
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Getter
@AllArgsConstructor
public enum MenuTypeEnums implements IEnum<String> {

    M("M", "目录"),
    C("C", "菜单"),
    B("B", "按钮"),
    ;

    @JsonValue
    @EnumValue
    private final String value;

    private final String desc;

    /**
     * 枚举值： vo返回 数据库存储字段
     *
     * @return 枚举值
     */
    public static List<String> getValues() {
        return Arrays.stream(MenuTypeEnums.values()).map(MenuTypeEnums::getValue).toList();
    }

    public static MenuTypeEnums ofValue(String value) {
        for (MenuTypeEnums enums : values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return null;
    }
}
