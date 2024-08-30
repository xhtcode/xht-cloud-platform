package com.xht.cloud.framework.security.utils;

import com.xht.cloud.framework.exception.user.UserException;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

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
     * 获取当前的登录账号
     *
     * @return 当前登录账号
     */
    public static String getUserAccount() {
        return user().orElseThrow(() -> new UserException("获取不到登录信息")).getNickName();
    }

    /**
     * 获取用户名称
     */
    public static String getUserName() {
        return user().orElseThrow(() -> new UserException("获取不到登录信息")).getUsername();
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


}
