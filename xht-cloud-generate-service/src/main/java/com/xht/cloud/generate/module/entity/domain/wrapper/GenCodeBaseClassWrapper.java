package com.xht.cloud.generate.module.entity.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.entity.domain.dataobject.GenCodeBaseClassDO;

import java.util.Objects;

/**
 * 描述 ：代码生成器-基类
 *
 * @author 小糊涂
 **/
public final class GenCodeBaseClassWrapper implements EntityWrapper<GenCodeBaseClassDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenCodeBaseClassDO> lambdaQuery(GenCodeBaseClassDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenCodeBaseClassDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), GenCodeBaseClassDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getClassName()), GenCodeBaseClassDO::getClassName, entity.getClassName())
                .eq(StringUtils.hasText(entity.getPackageName()), GenCodeBaseClassDO::getPackageName, entity.getPackageName())
                .eq(StringUtils.hasText(entity.getIgnoreField()), GenCodeBaseClassDO::getIgnoreField, entity.getIgnoreField())
                .eq(StringUtils.hasText(entity.getClassSort()), GenCodeBaseClassDO::getClassSort, entity.getClassSort())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenCodeBaseClassDO> lambdaUpdate(GenCodeBaseClassDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenCodeBaseClassDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenCodeBaseClassDO::getClassName, entity.getClassName())
                .set(GenCodeBaseClassDO::getPackageName, entity.getPackageName())
                .set(GenCodeBaseClassDO::getIgnoreField, entity.getIgnoreField())
                .set(GenCodeBaseClassDO::getClassSort, entity.getClassSort())
        ;
    }


}
