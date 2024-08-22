package com.xht.cloud.generate.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：生成代码异常
 *
 * @author 小糊涂
 **/
public class GenerateException extends BizException {

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

}
