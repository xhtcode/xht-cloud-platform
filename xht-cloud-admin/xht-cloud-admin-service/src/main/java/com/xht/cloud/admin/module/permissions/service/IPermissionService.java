package com.xht.cloud.admin.module.permissions.service;

import java.util.List;

/**
 * 描述 ：权限管理接口
 *
 * @author 小糊涂
 **/
public interface IPermissionService {

    /**
     * 角色授权菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     * @return {@link Boolean} true 成功 false 失败
     */
    boolean roleAuthorizationMenu(String roleId, List<String> menuIds);

    /**
     * 根据角色id 查询该角色所拥有的菜单
     *
     * @param roleId 角色id
     * @return {@link List<String>} 菜单id
     */
    List<String> selectMenuIdByRoleId(final String roleId);

    /**
     * 用户授权角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return {@link Boolean} true 成功 false 失败
     */
    boolean userAuthorizationRole(String userId, List<String> roleIds);

    /**
     * 根据用户id 查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return {@link List<String>} 角色id
     */
    List<String> selectRoleByUserId(final String userId);


}
