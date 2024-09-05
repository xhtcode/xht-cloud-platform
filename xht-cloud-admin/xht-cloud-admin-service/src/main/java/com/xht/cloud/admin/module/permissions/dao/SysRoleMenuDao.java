package com.xht.cloud.admin.module.permissions.dao;

import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleMenuDO;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMenuMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysRoleMenuDao extends BaseDaoImpl<SysRoleMenuMapper, SysRoleMenuDO> {

    /**
     * 跟据角色id删除
     *
     * @param roleId 角色id
     */
    public boolean deleteByRoleId(String roleId) {
        return remove(lambdaQuery().eq(SysRoleMenuDO::getRoleId, roleId));
    }

    /**
     * 判断菜单id是否已经绑定了角色
     *
     * @param menuIds 菜单id
     * @return 绑定true
     */
    public boolean existsByMenuId(List<String> menuIds) {
        return exists(lambdaQuery().in(SysRoleMenuDO::getMenuId, menuIds));
    }

    /**
     * 判断菜单id是否已经绑定了角色
     *
     * @param menuId 菜单id
     * @return 绑定true
     */
    public boolean existsByMenuId(String menuId) {
        return exists(lambdaQuery().eq(SysRoleMenuDO::getMenuId, menuId));
    }
}
