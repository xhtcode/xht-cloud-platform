package com.xht.cloud.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.handler.dto.DataScopeDTO;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public interface DataScopeSql {

    /**
     * @param dataScopeDTO       数据权限前置校验后返回的信息
     * @param builder            字段构建用户获取权限字段的name
     * @param lambdaQueryWrapper sql构建器
     */
    <T extends AbstractDO> void generate(DataScopeDTO dataScopeDTO, DataScopeFieldBuilder<T> builder, LambdaQueryWrapper<T> lambdaQueryWrapper);

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @param queryWrapper sql构建器
     */
    <T extends AbstractDO> void generate(DataScopeDTO dataScopeDTO, DataScopeFieldBuilder<T> builder, QueryWrapper<T> queryWrapper);

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @return sql 字符串
     */
    <T extends AbstractDO> String generate(DataScopeDTO dataScopeDTO, DataScopeFieldBuilder<T> builder);


}
