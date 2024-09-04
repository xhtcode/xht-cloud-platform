package com.xht.cloud.admin.exceptions;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：权限异常
 *
 * @author 小糊涂
 **/
public class PermissionException extends BizException {

    /**
     * @param message 异常描述
     */
    public PermissionException(String message) {
        super(message);
    }

}
