package com.xht.cloud.admin.module.permissions.controller;

import com.xht.cloud.admin.module.permissions.service.IPermissionService;
import com.xht.cloud.framework.domain.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述 ：权限管理接口
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/sys/permission")
@RequiredArgsConstructor
@Tag(name = "权限管理接口")
public class PermissionController {

    private final IPermissionService permissionService;

    /**
     * 角色授权菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     * @return {@link Boolean} true 成功 false 失败
     */
    @PostMapping("/role/authorization/{roleId}")
    public R<Boolean> roleAuthorizationMenu(@PathVariable("roleId") String roleId, @RequestBody List<String> menuIds) {
        return R.ok(permissionService.roleAuthorizationMenu(roleId, menuIds));
    }

    /**
     * 根据角色id 查询该角色所拥有的菜单
     *
     * @param roleId 角色id
     * @return {@link List<String>} 菜单id
     */
    @GetMapping("/menuId/{roleId}")
    public R<List<String>> selectMenuIdByRoleId(@PathVariable("roleId") String roleId) {
        return R.ok(permissionService.selectMenuIdByRoleId(roleId));
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return {@link Boolean} true 成功 false 失败
     */
    @PostMapping("/user/authorization/{userId}")
    public R<Boolean> userAuthorizationRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        return R.ok(permissionService.userAuthorizationRole(userId, roleIds));
    }

    /**
     * 根据用户id 查询该用户所拥有的角色
     *
     * @param userId 用户id
     * @return {@link List<String>} 角色id
     */
    @GetMapping("/roleId/{userId}")
    public R<List<String>> selectRoleByUserId(@PathVariable("userId") String userId) {
        return R.ok(permissionService.selectRoleByUserId(userId));
    }
}
