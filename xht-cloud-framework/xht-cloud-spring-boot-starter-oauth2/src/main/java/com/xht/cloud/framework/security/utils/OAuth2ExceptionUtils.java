package com.xht.cloud.framework.security.utils;

import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import com.xht.cloud.framework.security.constant.OAuth2ErrorStatusCode;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;

/**
 * 描述 ：oauth2 异常处理
 *
 * @author 小糊涂
 **/
public final class OAuth2ExceptionUtils {
    /**
     * 默认错误uri
     */
    public static String DEFAULT_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";


    /**
     * 抛出异常
     *
     * @param errorStatusCode {@link OAuth2ErrorStatusCode}
     */
    public static OAuth2AuthenticationException throwError(IErrorStatusCode errorStatusCode) {
        OAuth2Error error = new OAuth2Error(errorStatusCode.getCode().toString(), errorStatusCode.getMessage(), DEFAULT_ERROR_URI);
        return new OAuth2AuthorizationCodeRequestAuthenticationException(error, null);
    }

    /**
     * 抛出异常
     *
     * @param errorStatusCode {@link OAuth2ErrorStatusCode}
     */
    public static OAuth2AuthenticationException throwError(String message) {
        OAuth2Error error = new OAuth2Error("500", message, DEFAULT_ERROR_URI);
        return new OAuth2AuthorizationCodeRequestAuthenticationException(error, null);
    }
}
