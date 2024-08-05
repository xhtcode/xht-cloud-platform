package com.xht.cloud.framework.redis.exception;


import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：幂等异常
 *
 * @author 小糊涂
 **/
public class IdempotentException extends BizException {

    /**
     * @param message 异常描述
     */
    public IdempotentException(String message) {
        super(message);
    }

}
