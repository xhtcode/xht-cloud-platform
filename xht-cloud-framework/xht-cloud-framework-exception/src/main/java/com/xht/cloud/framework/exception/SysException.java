package com.xht.cloud.framework.exception;


import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

import java.io.Serial;

/**
 * 描述 ：系统异常
 *
 * @author 小糊涂
 **/
public class SysException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param message 异常描述
     */
    public SysException(String message) {
        super(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    /**
     * @param e {@link    Throwable}
     */
    public SysException(Throwable e) {
        super(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), e);
    }

    /**
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public SysException(String errMessage, Throwable e) {
        super(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR.getCode(), errMessage, e);
    }

    /**
     * @param errorStatusCode 异常状态码 {@link IErrorStatusCode}
     */
    public SysException(IErrorStatusCode errorStatusCode) {
        super(errorStatusCode);
    }
}
