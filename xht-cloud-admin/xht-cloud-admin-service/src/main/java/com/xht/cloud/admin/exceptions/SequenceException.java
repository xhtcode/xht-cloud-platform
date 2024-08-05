package com.xht.cloud.admin.exceptions;


import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class SequenceException extends BizException {

    /**
     * @param message 异常描述
     */
    public SequenceException(String message) {
        super(message);
    }
}
