package com.xht.cloud.admin.module.permissions.service.impl;

import com.xht.cloud.admin.exceptions.PermissionException;
import com.xht.cloud.admin.module.permissions.dao.SysRoleDao;
import com.xht.cloud.admin.module.permissions.dao.SysRoleMenuDao;
import com.xht.cloud.admin.module.permissions.dao.SysUserRoleDao;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleMenuDO;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysUserRoleDO;
import com.xht.cloud.admin.module.permissions.service.IPermissionService;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.tool.ExceptionTool;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述 ：权限管理
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {

    private final SysUserStaffDao sysUserStaffDao;

    private final SysRoleDao sysRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysUserRoleDao sysUserRoleDao;


    /**
     * 角色授权菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     * @return {@link Boolean} true 成功 false 失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean roleAuthorizationMenu(String roleId, List<String> menuIds) {
        ExceptionTool.permissionValidation(!StringUtils.hasText(roleId), "roleId is must  not null!");
        SysRoleDO sysRoleDO = sysRoleDao.getById(roleId);
        ExceptionTool.permissionValidation(Objects.isNull(sysRoleDO), "查询不到相关角色信息!");
        sysRoleMenuDao.deleteByRoleId(sysRoleDO.getId());
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<SysRoleMenuDO> menuDOList = new ArrayList<>(menuIds.size());
            SysRoleMenuDO sysRoleMenuDO;
            for (String item : menuIds) {
                sysRoleMenuDO = new SysRoleMenuDO();
                sysRoleMenuDO.setRoleId(roleId);
                sysRoleMenuDO.setMenuId(item);
                menuDOList.add(sysRoleMenuDO);
            }
            sysRoleMenuDao.saveBatch(menuDOList, 100);
        }
        return Boolean.TRUE;
    }

    /**
     * 根据角色id 查询该角色所拥有的菜单
     *
     * @param roleId 角色id
     * @return {@link List<String>} 菜单id
     */
    @Override
    public List<String> selectMenuIdByRoleId(String roleId) {
        List<SysRoleMenuDO> menuDOList = sysRoleMenuDao.selectList(SysRoleMenuDO::getRoleId, roleId);
        if (CollectionUtils.isEmpty(menuDOList)) {
            return Collections.emptyList();
        }
        return menuDOList.stream().map(SysRoleMenuDO::getMenuId).collect(Collectors.toList());
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return {@link Boolean} true 成功 false 失败
     */
    @Override
    public boolean userAuthorizationRole(String userId, List<String> roleIds) {
        ExceptionTool.permissionValidation(!StringUtils.hasText(userId), "userId is must  not null!");
        long userCount = sysUserStaffDao.selectCount(SysUserStaffDO::getId, userId);
        if (!SqlHelper.exist(userCount)) {
            throw new BizException("查询不到相关用户信息!");
        }
        sysUserRoleDao.removeByUserId(userId);
        if (!CollectionUtils.isEmpty(roleIds)) {
            long roleCount = sysRoleDao.selectCountIn(SysRoleDO::getId, roleIds);
            if (!SqlHelper.exist(roleCount)) {
                throw new PermissionException("查询不到相关角色信息!");
            }
            List<SysUserRoleDO> userRoleDOS = new ArrayList<>(roleIds.size());
            SysUserRoleDO sysUserRoleDO;
            for (String item : roleIds) {
                sysUserRoleDO = new SysUserRoleDO();
                sysUserRoleDO.setUserId(userId);
                sysUserRoleDO.setRoleId(item);
                userRoleDOS.add(sysUserRoleDO);
            }
            sysUserRoleDao.saveBatch(userRoleDOS, 100);
        }
        return Boolean.TRUE;
    }

    /**
     * 根据用户id 查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return {@link List<String>} 角色id
     */
    @Override
    public List<String> selectRoleByUserId(String userId) {
        return sysUserRoleDao.selectRoleIdList(userId);
    }
}
