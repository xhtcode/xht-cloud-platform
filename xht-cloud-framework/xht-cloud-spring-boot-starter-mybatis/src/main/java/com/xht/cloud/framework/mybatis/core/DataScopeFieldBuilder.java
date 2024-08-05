package com.xht.cloud.framework.mybatis.core;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@Builder
public final class DataScopeFieldBuilder<T extends AbstractDO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门数据权限字段
     */
    private SFunction<T, ?> deptField;

    /**
     * 用户数据权限字段
     */
    private SFunction<T, ?> userField;

    /**
     * 部门数据权限字段
     */
    private String deptStrField;

    /**
     * 用户数据权限字段
     */
    private String userStrField;

    private DataScopeFieldBuilder(SFunction<T, ?> deptField, SFunction<T, ?> userField, String deptStrField, String userStrField) {
        this.deptField = deptField;
        this.userField = userField;
        this.deptStrField = deptStrField;
        this.userStrField = userStrField;
    }


}
