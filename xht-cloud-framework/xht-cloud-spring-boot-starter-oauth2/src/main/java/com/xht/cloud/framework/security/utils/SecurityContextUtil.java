package com.xht.cloud.framework.security.utils;

import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 描述 ：springSecurity上下文对象
 *
 * @author 小糊涂
 **/
public final class SecurityContextUtil {

    /**
     * 获取Authentication
     */
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 获取登录的用户信息
     */
    public static Optional<UserDetailsBO> user() {
        try {
            Authentication authentication = getAuthentication().orElse(null);
            if (Objects.isNull(authentication)) {
                return Optional.empty();
            }
            return Optional.of((UserDetailsBO) authentication.getPrincipal());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 获取登录的用户信息
     */
    public static UserDetailsBO userNoNull() {
        return user().orElseThrow(() -> new UserException("用户登录信息获取失败"));
    }

    /**
     * 获取当前的登录账号
     *
     * @return 当前登录账号
     */
    public static String getUserAccount() {
        return userNoNull().getNickName();
    }

    /**
     * 获取用户名称
     */
    public static String getUserName() {
        return userNoNull().getUsername();
    }
    /**
     * 获取用户名称
     */
    public static String getUserId() {
        return userNoNull().getId();
    }



    /**
     * 判断当前登录用户是不是管理员
     *
     * @return {@link Boolean} true 是 false不是
     */
    public static boolean isAdmin() {
        return user().orElseGet(UserDetailsBO::new).isAdmin();
    }

    /**
     * 判断用户是否登录
     *
     * @return true 登录 false未登录
     */
    public static boolean isLogin() {
        Authentication authentication = getAuthentication().orElse(null);
        return Objects.nonNull(authentication) && authentication.isAuthenticated();
    }

    /**
     * 判断接口是否有任意xxx，xxx角色
     *
     * @param roleCodes 角色
     * @return {boolean}
     */
    public static boolean hasRoleCode(String... roleCodes) {
        if (ArrayUtil.isEmpty(roleCodes)) {
            return false;
        }
        UserDetailsBO userDetailsBO = userNoNull();
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
    public static boolean hasAnyAuthority(String... permissions) {
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        UserDetailsBO userDetailsBO = userNoNull();
        if (userDetailsBO.isAdmin()) {
            return true;
        }
        Set<String> menuCode = userDetailsBO.getMenuCode();
        if (CollectionUtils.isEmpty(menuCode)) return false;
        return menuCode.stream().anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
    }

}
