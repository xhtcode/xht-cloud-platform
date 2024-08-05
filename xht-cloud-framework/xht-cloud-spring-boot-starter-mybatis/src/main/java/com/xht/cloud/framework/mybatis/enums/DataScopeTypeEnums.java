package com.xht.cloud.framework.mybatis.enums;


import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：数据权限类别
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnums implements IEnum<String> {

    DEPT_USER_TYPE("部门级用户权限")

    ;


    private final String value;

}
