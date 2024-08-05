package com.xht.cloud.generate.module.template.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeGroupTemplateDO;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class GenCodeGroupTemplateWrapper implements EntityWrapper<GenCodeGroupTemplateDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenCodeGroupTemplateDO> lambdaQuery(GenCodeGroupTemplateDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenCodeGroupTemplateDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getGroupId()), GenCodeGroupTemplateDO::getGroupId, entity.getGroupId())
                .eq(StringUtils.hasText(entity.getTemplateId()), GenCodeGroupTemplateDO::getTemplateId, entity.getTemplateId())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenCodeGroupTemplateDO> lambdaUpdate(GenCodeGroupTemplateDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenCodeGroupTemplateDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
        ;
    }


}
