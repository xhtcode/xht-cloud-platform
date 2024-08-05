package com.xht.cloud.generate.module.template.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeGroupDO;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class GenCodeGroupWrapper implements EntityWrapper<GenCodeGroupDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenCodeGroupDO> lambdaQuery(GenCodeGroupDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenCodeGroupDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), GenCodeGroupDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getGroupName()), GenCodeGroupDO::getGroupName, entity.getGroupName())
                .eq(StringUtils.hasText(entity.getGroupDesc()), GenCodeGroupDO::getGroupDesc, entity.getGroupDesc())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenCodeGroupDO> lambdaUpdate(GenCodeGroupDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenCodeGroupDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenCodeGroupDO::getGroupName, entity.getGroupName())
                .set(GenCodeGroupDO::getGroupDesc, entity.getGroupDesc())
        ;
    }


}
