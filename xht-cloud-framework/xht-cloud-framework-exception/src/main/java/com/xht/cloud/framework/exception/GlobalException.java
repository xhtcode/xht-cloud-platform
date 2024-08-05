package com.xht.cloud.framework.exception;

import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
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

    public GlobalException(IErrorStatusCode errorStatusCode) {
        super(errorStatusCode.getMessage());
        this.code = errorStatusCode.getCode();
    }

    /**
     * @param errorStatusCode 异常状态码 {@link IErrorStatusCode}
     * @param e               {@link    Throwable}
     */
    public GlobalException(IErrorStatusCode errorStatusCode, Throwable e) {
        super(errorStatusCode.getMessage(), e);
        this.code = errorStatusCode.getCode();
    }

    /**
     * @param errCode    异常状态码
     * @param errMessage 异常描述
     * @param e          {@link    Throwable}
     */
    public GlobalException(Integer errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.code = errCode;
    }

}
