package com.xht.cloud.framework.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum OperateTypeEnums implements IEnum<Integer> {

    QUERY(1, "查询"),

    PAGE(2, "分页"),

    CREATE(3, "创建"),

    UPDATE(4, "修改"),

    DELETE(5, "删除"),

    EXPORT(6, "导出"),

    IMPORT(7, "导入"),

    OTHER(8, "其他"),

    ;

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OperateTypeEnums of(Integer value) {
        for (OperateTypeEnums item : values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }
}
