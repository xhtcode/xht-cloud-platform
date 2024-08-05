package com.xht.cloud.admin.exceptions;

import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

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

    /**
     * @param statusCode 业务异常状态码 {@link GlobalErrorStatusCode}
     */
    public PermissionException(IErrorStatusCode statusCode) {
        super(statusCode);
    }
}
