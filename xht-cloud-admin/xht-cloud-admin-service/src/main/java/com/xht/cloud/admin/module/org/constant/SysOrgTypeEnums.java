package com.xht.cloud.admin.module.org.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysOrgTypeEnums implements IEnum<String> {

    /**
     * 公司
     */
    COMPANY("COMPANY"),

    /**
     * 部门
     */
    DEPT("DEPT");

    @JsonValue
    @EnumValue
    private final String value;

    public static void validate(String value) {
        boolean flag = COMPANY.getValue().equals(value) || DEPT.getValue().equals(value);
        if (!flag) {
            throw new BizException("不支持的组织分类");
        }
    }
}