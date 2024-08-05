package com.xht.cloud.admin.module.dept.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysRoleDeptDO;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：角色和部门关联
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysRoleDeptConvert extends EntityWrapper<SysRoleDeptDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysRoleDeptDO> lambdaQuery(SysRoleDeptDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysRoleDeptDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(!ObjectUtils.isEmpty(entity.getRoleId()), SysRoleDeptDO::getRoleId, entity.getRoleId())
                .eq(!ObjectUtils.isEmpty(entity.getDeptId()), SysRoleDeptDO::getDeptId, entity.getDeptId())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysRoleDeptDO> lambdaUpdate(SysRoleDeptDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysRoleDeptDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                ;
    }

}
