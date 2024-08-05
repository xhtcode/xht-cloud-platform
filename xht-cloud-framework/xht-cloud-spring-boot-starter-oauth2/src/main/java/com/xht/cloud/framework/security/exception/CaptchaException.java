package com.xht.cloud.framework.security.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：验证码异常
 *
 * @author : 小糊涂
 **/
public class CaptchaException extends BizException {

    /**
     * @param message 异常描述
     */
    public CaptchaException(String message) {
        super(message);
    }
}
