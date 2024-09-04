package com.xht.cloud.framework.security.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：验证码异常
 *
 * @author : 小糊涂
 **/
public class CaptchaException extends BizException {

    public static final String ERROR_MSG = "验证码错误";

    /**
     * @param message 异常描述
     */
    public CaptchaException() {
        super(ERROR_MSG);
    }
}
