package com.xht.cloud.framework.security.web;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import static com.xht.cloud.framework.exception.constant.UserErrorStatusCode.OTHER_DEVICE_LOGIN;

/**
 * 描述 ：在LogoutFilter成功注销后调用的策略，以处理重定向或转发到适当的目的地。
 * 注意，该接口与LogoutHandler几乎相同，但可能引发异常。LogoutHandler实现期望被调用来执行必要的清理，因此不应该抛出异常
 *
 * @author 小糊涂
 **/
@Slf4j
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authenticationName = authentication.getName();
        log.debug("{} 退出成功", authenticationName);
        HttpServletUtils.writeString(response, R.failed().format(OTHER_DEVICE_LOGIN));
    }
}
