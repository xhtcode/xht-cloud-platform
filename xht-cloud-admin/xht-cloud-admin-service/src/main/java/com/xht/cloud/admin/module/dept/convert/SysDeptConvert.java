package com.xht.cloud.admin.module.dept.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysDeptConvert extends IBaseConvert<SysDeptCreateRequest, SysDeptUpdateRequest, SysDeptQueryRequest, SysDeptResponse, SysDeptDO>, EntityWrapper<SysDeptDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysDeptDO> lambdaQuery(SysDeptDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysDeptDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysDeptDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getParentId()), SysDeptDO::getParentId, entity.getParentId())
                .like(StringUtils.hasText(entity.getDeptName()), SysDeptDO::getDeptName, entity.getDeptName())
                .like(StringUtils.hasText(entity.getDeptCode()), SysDeptDO::getDeptCode, entity.getDeptCode())
                .eq(Objects.nonNull(entity.getDeptStatus()), SysDeptDO::getDeptStatus, entity.getDeptStatus())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysDeptDO> lambdaUpdate(SysDeptDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysDeptDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysDeptDO::getParentId, entity.getParentId())
                .set(SysDeptDO::getDirectorId, entity.getDirectorId())
                .set(SysDeptDO::getDeptName, entity.getDeptName())
                .set(SysDeptDO::getDeptCode, entity.getDeptCode())
                .set(SysDeptDO::getDeptLeader, entity.getDeptLeader())
                .set(SysDeptDO::getDeptTel, entity.getDeptTel())
                .set(SysDeptDO::getDeptSort, entity.getDeptSort())
                .set(SysDeptDO::getDeptStatus, entity.getDeptStatus())
                .set(SysDeptDO::getDescription, entity.getDescription())
                ;
    }
}
