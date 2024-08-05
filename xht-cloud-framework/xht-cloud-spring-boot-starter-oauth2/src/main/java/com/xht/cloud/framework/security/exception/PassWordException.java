package com.xht.cloud.framework.security.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ： 密码异常
 *
 * @author 小糊涂
 **/
public class PassWordException extends BizException {
    /**
     * @param message 异常描述
     */
    public PassWordException(String message) {
        super(message);
    }
}
