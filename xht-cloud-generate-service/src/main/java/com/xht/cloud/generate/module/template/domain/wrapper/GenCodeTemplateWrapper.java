package com.xht.cloud.generate.module.template.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;

import java.util.Objects;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
public final class GenCodeTemplateWrapper implements EntityWrapper<GenCodeTemplateDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenCodeTemplateDO> lambdaQuery(GenCodeTemplateDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenCodeTemplateDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), GenCodeTemplateDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getGroupId()), GenCodeTemplateDO::getGroupId, entity.getGroupId())
                .eq(StringUtils.hasText(entity.getTelName()), GenCodeTemplateDO::getTelName, entity.getTelName())
                .eq(StringUtils.hasText(entity.getTelRemark()), GenCodeTemplateDO::getTelRemark, entity.getTelRemark())
                .eq(StringUtils.hasText(entity.getTelContent()), GenCodeTemplateDO::getTelContent, entity.getTelContent())
                .eq(StringUtils.hasText(entity.getTelFileType()), GenCodeTemplateDO::getTelFileType, entity.getTelFileType())
                .eq(StringUtils.hasText(entity.getTelStatus()), GenCodeTemplateDO::getTelStatus, entity.getTelStatus())
                .eq(StringUtils.hasText(entity.getIgnoreField()), GenCodeTemplateDO::getIgnoreField, entity.getIgnoreField())
                .eq(Objects.nonNull(entity.getTelSort()), GenCodeTemplateDO::getTelSort, entity.getTelSort())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenCodeTemplateDO> lambdaUpdate(GenCodeTemplateDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenCodeTemplateDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenCodeTemplateDO::getIgnoreField, entity.getIgnoreField())
                .set(GenCodeTemplateDO::getGroupId, entity.getGroupId())
                .set(GenCodeTemplateDO::getTelName, entity.getTelName())
                .set(GenCodeTemplateDO::getTelRemark, entity.getTelRemark())
                .set(GenCodeTemplateDO::getTelContent, entity.getTelContent())
                .set(GenCodeTemplateDO::getTelFileType, entity.getTelFileType())
                .set(GenCodeTemplateDO::getTelStatus, entity.getTelStatus())
                .set(GenCodeTemplateDO::getTelSort, entity.getTelSort())
        ;
    }


}
