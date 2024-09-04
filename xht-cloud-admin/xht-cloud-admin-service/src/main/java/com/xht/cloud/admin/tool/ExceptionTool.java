package com.xht.cloud.admin.tool;

import com.xht.cloud.admin.exceptions.MenuException;
import com.xht.cloud.admin.exceptions.PermissionException;

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

}
