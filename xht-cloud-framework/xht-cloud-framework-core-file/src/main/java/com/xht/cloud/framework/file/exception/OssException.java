package com.xht.cloud.framework.file.exception;


import com.xht.cloud.framework.file.enums.OssErrorStatusCode;

import static com.xht.cloud.framework.exception.constant.IErrorStatusCode.DEFAULT_ERR_CODE;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class OssException extends FileException {
    /**
     * @param message 异常描述
     */
    public OssException(String message) {
        super(message);
    }

    /**
     * @param statusCode 业务异常状态码 {@link OssErrorStatusCode}
     */
    public OssException(OssErrorStatusCode statusCode) {
        super(statusCode);
    }

    public OssException(Throwable e) {
        super(DEFAULT_ERR_CODE, e.getLocalizedMessage(), e);
    }

}
