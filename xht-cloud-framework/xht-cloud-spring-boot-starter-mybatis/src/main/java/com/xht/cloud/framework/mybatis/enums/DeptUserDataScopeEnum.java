package com.xht.cloud.framework.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * 描述 ： 部门级 数据权限
 * 数据权限
 * <li class="el-select-dropdown__item"><span>1:全部数据权限</span></li>
 * <li class="el-select-dropdown__item selected"><span>2:自定数据权限</span></li>
 * <li class="el-select-dropdown__item hover"><span>3:本部门数据权限</span></li>
 * <li class="el-select-dropdown__item"><span>4:本部门及以下数据权限</span></li>
 * <li class="el-select-dropdown__item"><span>5:仅本人数据权限</span></li>
 *
 * @author 小糊涂
 **/
public enum DeptUserDataScopeEnum implements IEnum<Integer> {

    DATA_SCOPE_ALL(1, "全部数据权限"),

    DATA_SCOPE_CUSTOM(2, "自定数据权限"),

    DATA_SCOPE_DEPT_AND_CHILD(3, "本部门及以下数据权限"),

    DATA_SCOPE_DEPT(4, "本部门数据权限"),


    DATA_SCOPE_SELF(5, "本人数据");

    @JsonValue    //标记响应json值
    @EnumValue//标记数据库存的值是code
    private final Integer value;

    @Getter
    private final String label;

    DeptUserDataScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public static DeptUserDataScopeEnum getDataScope(Integer value) {
        DeptUserDataScopeEnum[] values = DeptUserDataScopeEnum.values();
        for (DeptUserDataScopeEnum deptUserDataScopeEnum : values) {
            if (Objects.equals(deptUserDataScopeEnum.getValue(), value)) {
                return deptUserDataScopeEnum;
            }
        }
        return null;
    }

    /**
     * 枚举数据库存储值
     */
    @Override
    public Integer getValue() {
        return this.value;
    }

}
