package com.xht.cloud.framework.starter.exception;


import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

/**
 * 描述 ：签名验证失败
 *
 * @author 小糊涂
 **/
public class ApiSignatureException extends BizException {

    /**
     * @param message 异常描述
     */
    public ApiSignatureException(String message) {
        super(message);
    }

    /**
     * @param statusCode 业务异常状态码 {@link IErrorStatusCode}
     */
    public ApiSignatureException(IErrorStatusCode statusCode) {
        super(statusCode);
    }
}
