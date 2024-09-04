package com.xht.cloud.framework.exception;


import java.io.Serial;

import static com.xht.cloud.framework.exception.constant.IErrorStatusCode.DEFAULT_ERROR_CODE;

/**
 * 描述 ：系统异常
 *
 * @author 小糊涂
 **/
public class SysException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String ERROR_MSG = "错误的配置项";

    public SysException() {
        super(DEFAULT_ERROR_CODE, ERROR_MSG);
    }

    /**
     * @param message 异常描述
     */
    public SysException(String message) {
        super(DEFAULT_ERROR_CODE, message, null);
    }

    /**
     * @param e {@link    Throwable}
     */
    public SysException(Throwable e) {
        super(DEFAULT_ERROR_CODE, e.getMessage(), e);
    }

}
