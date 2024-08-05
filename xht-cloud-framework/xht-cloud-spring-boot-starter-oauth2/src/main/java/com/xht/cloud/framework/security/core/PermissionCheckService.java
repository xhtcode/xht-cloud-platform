package com.xht.cloud.framework.security.core;

import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Set;

/**
 * 描述 ： 权限校验
 *
 * @author 小糊涂
 **/
public class PermissionCheckService {

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
