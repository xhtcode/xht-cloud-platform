package com.xht.cloud.framework.jackson.translation.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ： 字典翻译异常
 *
 * @author : 小糊涂
 **/
public class TransDictException extends BizException {

    /**
     * @param message 异常描述
     */
    public TransDictException(String message) {
        super(message);
    }

    /**
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public TransDictException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}
