package com.xht.cloud.framework.web.validation.exception;

import com.xht.cloud.framework.exception.BizException;
import lombok.Getter;

/**
 * 描述 ：校验异常
 *
 * @author : 小糊涂
 **/
@Getter
public class ValidationException extends BizException {

    private final String filedName;

    /**
     * @param message 异常描述
     */
    public ValidationException(String filedName, String message) {
        super(message);
        this.filedName = filedName;
    }
}
