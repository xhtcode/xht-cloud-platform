package com.xht.cloud.framework.file.exception;


import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;

/**
 * 描述 ： 文件异常
 *
 * @author 小糊涂
 **/
public class FileException extends BizException {

    /**
     * @param message 异常描述
     */
    public FileException(String message) {
        super(message);
    }

    /**
     * @param statusCode 业务异常状态码 {@link IErrorStatusCode}
     */
    public FileException(IErrorStatusCode statusCode) {
        super(statusCode);
    }

    public FileException(String message, Throwable e) {
        super(message, e);
    }
}
