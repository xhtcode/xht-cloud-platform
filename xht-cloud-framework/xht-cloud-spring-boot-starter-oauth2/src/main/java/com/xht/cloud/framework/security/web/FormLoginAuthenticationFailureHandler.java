package com.xht.cloud.framework.security.web;

import com.xht.cloud.framework.exception.constant.UserErrorStatusCode;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 描述 ：认证失败时的处理
 *
 * @author 小糊涂
 **/
@Slf4j
public class FormLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 当身份验证尝试失败时调用.
     *
     * @param request   发生身份验证尝试的请求。
     * @param response  响应.
     * @param exception 为拒绝身份验证请求而抛出的异常。
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //获取错误信息
        String localizedMessage = exception.getLocalizedMessage();
        log.error("当身份验证失败 {}", localizedMessage, exception);
        HttpServletUtils.writeString(response, R.create(Boolean.FALSE).format(UserErrorStatusCode.AUTHENTICATION_FAILURE).setMsg(localizedMessage));
    }

}
