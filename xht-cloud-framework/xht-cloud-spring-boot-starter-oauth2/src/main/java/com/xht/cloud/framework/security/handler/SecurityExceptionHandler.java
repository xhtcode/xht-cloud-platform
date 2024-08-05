package com.xht.cloud.framework.security.handler;

import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.security.exception.OAuth2Exception;
import com.xht.cloud.framework.security.exception.PassWordException;
import com.xht.cloud.framework.core.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常拦截
 *
 * @author 小糊涂
 **/
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {


    /**
     * 捕获 {@code AccessDeniedException} 异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public R<String> handler(AccessDeniedException e) {
        String msg = SpringSecurityMessageSource.getAccessor()
                .getMessage("AbstractAccessDecisionManager.accessDenied", e.getMessage());
        log.warn("拒绝授权异常信息：{}", msg);
        return R.failed(GlobalErrorStatusCode.FORBIDDEN);
    }


    /**
     * 捕获 {@code AccessDeniedException} 异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OAuth2Exception.class)
    public R<String> handler(OAuth2Exception e) {
        log.warn("拒绝授权异常信息：{}", e.getMessage());
        return R.failed(GlobalErrorStatusCode.FORBIDDEN);
    }

    /**
     * 捕获 {@code PassWordException} 异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PassWordException.class)
    public R<String> handler(PassWordException e) {
        String msg = SpringSecurityMessageSource.getAccessor()
                .getMessage("AbstractAccessDecisionManager.accessDenied", e.getMessage());
        log.warn("密码错误：{}", msg);
        return R.failed(GlobalErrorStatusCode.FORBIDDEN);
    }

}
