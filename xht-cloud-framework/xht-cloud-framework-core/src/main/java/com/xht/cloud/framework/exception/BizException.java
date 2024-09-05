package com.xht.cloud.framework.exception;


import cn.hutool.core.text.StrFormatter;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

import java.io.Serial;

import static com.xht.cloud.framework.exception.constant.IErrorStatusCode.DEFAULT_ERROR_CODE;

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
        super(DEFAULT_ERROR_CODE, message, null);
    }

    /**
     * @param message 异常描述
     * @param argArray 异常描述参数列表
     */
    public BizException(String message, Object... argArray) {
        super(DEFAULT_ERROR_CODE, StrFormatter.format(message, argArray));
    }

    /**
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public BizException(String errMessage, Throwable e) {
        super(DEFAULT_ERROR_CODE, errMessage, e);
    }

    /**
     * @param statusCode 业务异常状态码 {@link IErrorStatusCode}
     */
    public BizException(IErrorStatusCode statusCode) {
        super(statusCode.getCode(), statusCode.getMessage(), null);
    }

    /**
     * @param statusCode 业务异常状态码 {@link IErrorStatusCode}
     * @param e          {@link    Throwable}
     */
    public BizException(IErrorStatusCode statusCode, Throwable e) {
        super(statusCode.getCode(), statusCode.getMessage(), e);
    }

    /**
     * @param errCode 异常状态码
     * @param message 异常描述
     * @param e       {@link    Throwable}
     */
    public BizException(Integer errCode, String message, Throwable e) {
        super(errCode, message, e);
    }
}
