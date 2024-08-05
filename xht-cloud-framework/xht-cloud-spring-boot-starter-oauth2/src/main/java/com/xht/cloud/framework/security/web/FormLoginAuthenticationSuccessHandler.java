package com.xht.cloud.framework.security.web;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 描述 ：认证成功时的处理
 *
 * @author 小糊涂
 **/
@Slf4j
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 当用户已成功通过身份验证时调用
     *
     * @param request        认证成功的请求
     * @param response       响应
     * @param authentication 认证过程过程中创建的<tt>Authentication</tt>对象
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取用户身份信息
        Object principal = authentication.getPrincipal();
        log.debug("登录成功：{}", principal);
        HttpServletUtils.writeString(response, R.ok().setMsg("登录成功").setData(principal));
    }

}
