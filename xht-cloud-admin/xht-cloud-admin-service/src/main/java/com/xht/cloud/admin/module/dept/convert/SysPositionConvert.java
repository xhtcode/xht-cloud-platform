package com.xht.cloud.admin.module.dept.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysPositionDO;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysPositionResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysPositionConvert extends IBaseConvert<SysPositionCreateRequest, SysPositionUpdateRequest, SysPositionQueryRequest, SysPositionResponse, SysPositionDO>, EntityWrapper<SysPositionDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysPositionDO> lambdaQuery(SysPositionDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysPositionDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysPositionDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getParentId()), SysPositionDO::getParentId, entity.getParentId())
                .eq(StringUtils.hasText(entity.getDeptId()), SysPositionDO::getDeptId, entity.getDeptId())
                .eq(StringUtils.hasText(entity.getPositionCode()), SysPositionDO::getPositionCode, entity.getPositionCode())
                .eq(StringUtils.hasText(entity.getPositionName()), SysPositionDO::getPositionName, entity.getPositionName())
                .eq(!ObjectUtils.isEmpty(entity.getSort()), SysPositionDO::getSort, entity.getSort())
                .eq(!ObjectUtils.isEmpty(entity.getStatus()), SysPositionDO::getStatus, entity.getStatus())
                .eq(StringUtils.hasText(entity.getDescription()), SysPositionDO::getDescription, entity.getDescription())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysPositionDO> lambdaUpdate(SysPositionDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysPositionDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysPositionDO::getParentId, entity.getParentId())
                .set(SysPositionDO::getDeptId, entity.getDeptId())
                .set(SysPositionDO::getPositionCode, entity.getPositionCode())
                .set(SysPositionDO::getPositionName, entity.getPositionName())
                .set(SysPositionDO::getSort, entity.getSort())
                .set(SysPositionDO::getStatus, entity.getStatus())
                .set(SysPositionDO::getDescription, entity.getDescription())
                ;
    }
}
