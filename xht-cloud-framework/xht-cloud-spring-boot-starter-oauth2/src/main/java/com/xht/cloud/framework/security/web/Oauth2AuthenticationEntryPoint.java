package com.xht.cloud.framework.security.web;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.exception.constant.UserErrorStatusCode;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 描述 ：请求未认证接口
 * 当访问一个需要认证之后才能访问的接口的时候，Spring Security会使用`AuthenticationEntryPoint`将用户请求跳转到登录页面，要求用户提供登录凭证。
 * <p>
 * 这里我们也希望系统`返回json结果`，因此我们定义类`实现AuthenticationEntryPoint接口`
 *
 * @author 小糊涂
 **/
@Slf4j
public class Oauth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 启动认证方案。
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String localizedMessage = authException.getLocalizedMessage();
        log.debug("请求未认证：{}", localizedMessage, authException);
        HttpServletUtils.writeString(response, R.failed(UserErrorStatusCode.NO_LOGIN));
    }

}
