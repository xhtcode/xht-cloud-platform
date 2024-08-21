package com.xht.cloud.generate.module.table.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;

import java.util.Objects;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
public final class GenTableWrapper implements EntityWrapper<GenTableDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenTableDO> lambdaQuery(GenTableDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenTableDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), GenTableDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getConfigId()), GenTableDO::getConfigId, entity.getConfigId())
                .like(StringUtils.hasText(entity.getTableName()), GenTableDO::getTableName, entity.getTableName())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenTableDO> lambdaUpdate(GenTableDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenTableDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenTableDO::getConfigId, entity.getConfigId())
                .set(GenTableDO::getGenDbId, entity.getGenDbId())
                .set(GenTableDO::getTableSchema, entity.getTableSchema())
                .set(GenTableDO::getTableEngine, entity.getTableEngine())
                .set(GenTableDO::getTableName, entity.getTableName())
                .set(GenTableDO::getModuleName, entity.getModuleName())
                .set(GenTableDO::getServiceDesc, entity.getServiceDesc())
                .set(GenTableDO::getServiceName, entity.getServiceName())
                .set(GenTableDO::getAuthorizationPrefix, entity.getAuthorizationPrefix())
                .set(GenTableDO::getPathUrl, entity.getPathUrl())
                .set(GenTableDO::getCodeName, entity.getCodeName())
                .set(GenTableDO::getTableCreateTime, entity.getTableCreateTime())
                .set(GenTableDO::getTableUpdateTime, entity.getTableUpdateTime())
        ;
    }


}
