package com.xht.cloud.admin.module.config.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.config.domain.dataobject.SysConfigDO;
import com.xht.cloud.admin.module.config.domain.request.SysConfigCreateRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigQueryRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigUpdateRequest;
import com.xht.cloud.admin.module.config.domain.response.SysConfigResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：系统配置信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysConfigConvert extends IBaseConvert<SysConfigCreateRequest, SysConfigUpdateRequest, SysConfigQueryRequest, SysConfigResponse, SysConfigDO>, EntityWrapper<SysConfigDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysConfigDO> lambdaQuery(SysConfigDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysConfigDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysConfigDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getConfigCode()), SysConfigDO::getConfigCode, entity.getConfigCode())
                .eq(StringUtils.hasText(entity.getConfigName()), SysConfigDO::getConfigName, entity.getConfigName())
                .eq(StringUtils.hasText(entity.getConfigInfo()), SysConfigDO::getConfigInfo, entity.getConfigInfo())
                .eq(StringUtils.hasText(entity.getClassName()), SysConfigDO::getClassName, entity.getClassName())
                .eq(StringUtils.hasText(entity.getDescription()), SysConfigDO::getDescription, entity.getDescription())
                .eq(!ObjectUtils.isEmpty(entity.getStatus()), SysConfigDO::getStatus, entity.getStatus())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysConfigDO> lambdaUpdate(SysConfigDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysConfigDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysConfigDO::getConfigCode, entity.getConfigCode())
                .set(SysConfigDO::getConfigName, entity.getConfigName())
                .set(SysConfigDO::getConfigInfo, entity.getConfigInfo())
                .set(SysConfigDO::getClassName, entity.getClassName())
                .set(SysConfigDO::getDescription, entity.getDescription())
                .set(SysConfigDO::getStatus, entity.getStatus())
                ;
    }

}
