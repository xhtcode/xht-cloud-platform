package com.xht.cloud.framework.security.exception;

import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：自定义oauth2异常
 *
 * @author 小糊涂
 **/
public class OAuth2Exception extends BizException {

    /**
     * @param message 异常描述
     */
    public OAuth2Exception(String message) {
        super(message);
    }

}
