package com.xht.cloud.framework.file.exception;


import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import com.xht.cloud.framework.file.enums.OssErrorStatusCode;

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
     * @param statusCode 业务异常状态码 {@link OssErrorStatusCode}
     */
    public FileException(IErrorStatusCode statusCode) {
        super(statusCode);
    }

    public FileException(Integer errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }
}
