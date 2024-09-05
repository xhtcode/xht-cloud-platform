package com.xht.cloud.admin.exceptions;


import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：自定义字典异常
 *
 * @author 小糊涂
 **/
public class DictException extends BizException {

    /**
     * @param message 异常描述
     */
    public DictException(String message) {
        super(message);
    }

    /**
     * @param message  异常描述
     * @param argArray 异常描述参数列表
     */
    public DictException(String message, Object... argArray) {
        super(message, argArray);
    }
}
