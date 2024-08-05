package com.xht.cloud.framework.redis.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：接口限流 异常
 *
 * @author 小糊涂
 **/
public class RequestLimitException extends BizException {

    /**
     * @param message 异常描述
     */
    public RequestLimitException(String message) {
        super(message);
    }
}
