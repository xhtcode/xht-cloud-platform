package com.xht.cloud.framework.exception;


import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

import java.io.Serial;

/**
 * 描述 ： 业务异常
 *
 * @author 小糊涂
 **/
public class BizException extends GlobalException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param message 异常描述
     */
    public BizException(String message) {
        super(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }


    /**
     * @param statusCode 业务异常状态码 {@link IErrorStatusCode}
     */
    public BizException(IErrorStatusCode statusCode) {
        super(statusCode, null);
    }

    /**
     * @param errCode    异常状态码
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public BizException(Integer errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }

    /**
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public BizException(String errMessage, Throwable e) {
        super(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR.getCode(), errMessage, e);
    }

}
