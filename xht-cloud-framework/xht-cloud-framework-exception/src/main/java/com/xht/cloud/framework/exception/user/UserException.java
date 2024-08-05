package com.xht.cloud.framework.exception.user;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：用户异常
 *
 * @author 小糊涂
 **/
public class UserException extends BizException {
    /**
     * @param message 异常描述
     */
    public UserException(String message) {
        super(message);
    }
}
