package com.xht.cloud.admin.tool;

import com.xht.cloud.admin.exceptions.MenuException;
import com.xht.cloud.admin.exceptions.PermissionException;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserDO;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.user.UserNotFountException;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;

import static com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode.SYSTEM_FORBIDDEN;

/**
 * 描述 ：异常判断抛出工具类
 *
 * @author 小糊涂
 **/
public final class ExceptionTool {

    /**
     * 菜单校验
     *
     * @param flag    是否执行
     * @param message 错误信息
     */
    public static void menuValidation(boolean flag, String message) {
        if (flag) {
            throw new MenuException(message);
        }
    }

    /**
     * 权限异常
     *
     * @param flag    是否执行
     * @param message 错误信息
     */
    public static void permissionValidation(boolean flag, String message) {
        if (flag) {
            throw new PermissionException(message);
        }
    }

    /**
     * 权限异常
     *
     * @param sysUserDO 用户信息
     */
    public static void permissionValidation(SysUserDO sysUserDO) {
        Assert.notNull(sysUserDO, UserNotFountException::new);
        if (sysUserDO.getAdminStatus().isAdmin() && !SecurityContextUtil.isAdmin()) {
            throw new PermissionException(SYSTEM_FORBIDDEN);
        }
    }

}
