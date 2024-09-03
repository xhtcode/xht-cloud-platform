package com.xht.cloud.framework.security.core;

import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Objects;
import java.util.Set;

/**
 * 描述 ： 权限校验
 *
 * @author 小糊涂
 **/
public class PermissionCheckService {

    /**
     * 查询用户id是否当前登录的用户id 如果是管理员直接返回true
     *
     * @param userId 用户id
     * @return true是
     */
    public boolean checkCurrentUserId(String userId) {
        UserDetailsBO userDetailsBO = SecurityContextUtil.userNoNull();
        return userDetailsBO.isAdmin() || Objects.equals(userId, userDetailsBO.getId());
    }

    /**
     * 查询用户账号是否当前登录的用户账号 如果是管理员直接返回true
     *
     * @param userName 用户账号
     * @return true是
     */
    public boolean checkCurrentUserName(String userName) {
        UserDetailsBO userDetailsBO = SecurityContextUtil.userNoNull();
        return userDetailsBO.isAdmin() || Objects.equals(userName, userDetailsBO.getUsername());
    }
    /**
     * 判断接口是否有任意xxx，xxx角色
     *
     * @param roleCodes 角色
     * @return {boolean}
     */
    public boolean hasRoleCode(String... roleCodes) {
        if (ArrayUtil.isEmpty(roleCodes)) {
            return false;
        }
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseGet(UserDetailsBO::new);
        if (userDetailsBO.isAdmin()) {
            return true;
        }
        Set<String> roles = userDetailsBO.getRoleCode();
        if (CollectionUtils.isEmpty(roles)) return false;
        return roles.stream().anyMatch(x -> PatternMatchUtils.simpleMatch(roleCodes, x));
    }

    /**
     * 判断接口是否有任意xxx，xxx权限
     *
     * @param permissions 权限
     * @return {boolean}
     */
    public boolean hasAnyAuthority(String... permissions) {
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        UserDetailsBO userDetailsBO = SecurityContextUtil.user().orElseGet(UserDetailsBO::new);
        if (userDetailsBO.isAdmin()) {
            return true;
        }
        Set<String> menuCode = userDetailsBO.getMenuCode();
        if (CollectionUtils.isEmpty(menuCode)) return false;
        return menuCode.stream().anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }
}
