package com.xht.cloud.admin.module.permissions.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysUserRoleDO;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：用户角色
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysUserRoleConvert extends EntityWrapper<SysUserRoleDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysUserRoleDO> lambdaQuery(SysUserRoleDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysUserRoleDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(!ObjectUtils.isEmpty(entity.getUserId()), SysUserRoleDO::getUserId, entity.getUserId())
                .eq(!ObjectUtils.isEmpty(entity.getRoleId()), SysUserRoleDO::getRoleId, entity.getRoleId())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysUserRoleDO> lambdaUpdate(SysUserRoleDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        return new LambdaUpdateWrapper<>()
                ;
    }

}
