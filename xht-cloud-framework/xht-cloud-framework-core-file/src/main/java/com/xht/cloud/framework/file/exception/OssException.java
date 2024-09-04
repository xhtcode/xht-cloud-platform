package com.xht.cloud.framework.file.exception;


import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class OssException extends FileException {

    private static final String ERROR_MSG = "OSS 存储异常";

    /**
     * @param message 异常描述
     */
    public OssException(String message) {
        super(message);
    }

    /**
     * @param statusCode 业务异常状态码 {@link OssErrorStatusCode}
     */
    public OssException(IErrorStatusCode statusCode) {
        super(statusCode);
    }

    public OssException(Throwable e) {
        super(ERROR_MSG, e);
    }

}
