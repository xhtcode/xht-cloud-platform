package com.xht.cloud.framework.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 描述 ：自定义异常基础类
 *
 * @author 小糊涂
 **/
@Getter
public class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer code;

    /**
     * @param errCode 异常状态码
     * @param message 异常描述
     */
    public GlobalException(Integer errCode, String message) {
        super(message);
        this.code = errCode;
    }

    /**
     * @param errCode    异常状态码
     * @param message    异常描述
     * @param e          {@link    Throwable}
     */
    public GlobalException(Integer errCode, String message, Throwable e) {
        super(message, e);
        this.code = errCode;
    }


}
