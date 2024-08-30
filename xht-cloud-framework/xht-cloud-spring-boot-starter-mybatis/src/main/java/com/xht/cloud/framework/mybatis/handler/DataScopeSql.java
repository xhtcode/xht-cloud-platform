package com.xht.cloud.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.handler.dto.DataScopeDTO;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public interface DataScopeSql<T extends IEnum<?>> {

    /**
     * @param dataScopeDTO       数据权限前置校验后返回的信息
     * @param builder            字段构建用户获取权限字段的name
     * @param lambdaQueryWrapper sql构建器
     */
    <DO extends AbstractDO> void generate(DataScopeDTO<T> dataScopeDTO, DataScopeFieldBuilder<DO> builder, LambdaQueryWrapper<DO> lambdaQueryWrapper);

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @param queryWrapper sql构建器
     */
    <DO extends AbstractDO> void generate(DataScopeDTO<T> dataScopeDTO, DataScopeFieldBuilder<DO> builder, QueryWrapper<DO> queryWrapper);

    /**
     * @param dataScopeDTO 数据权限前置校验后返回的信息
     * @param builder      字段构建用户获取权限字段的name
     * @return sql 字符串
     */
    <DO extends AbstractDO> String generate(DataScopeDTO<T> dataScopeDTO, DataScopeFieldBuilder<DO> builder);


}
