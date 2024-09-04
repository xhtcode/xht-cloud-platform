package com.xht.cloud.admin.module.permissions.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleMenuDO;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * 描述 ：系统角色菜单关联表
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysRoleMenuConvert extends EntityWrapper<SysRoleMenuDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysRoleMenuDO> lambdaQuery(SysRoleMenuDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysRoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getRoleId()), SysRoleMenuDO::getRoleId, entity.getRoleId())
                .eq(StringUtils.hasText(entity.getMenuId()), SysRoleMenuDO::getMenuId, entity.getMenuId())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysRoleMenuDO> lambdaUpdate(SysRoleMenuDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysRoleMenuDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysRoleMenuDO::getRoleId, entity.getRoleId())
                .set(SysRoleMenuDO::getMenuId, entity.getMenuId())
                ;
    }


}
