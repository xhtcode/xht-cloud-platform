package com.xht.cloud.framework.security.web;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 描述 ：请求未授权的接口
 *
 * @author 小糊涂
 **/
@Slf4j
public class UnAuthorizedAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理拒绝访问失败
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = accessDeniedException.getMessage();
        log.error("请求未授权：{}", message, accessDeniedException);
        HttpServletUtils.writeString(response, R.failed(GlobalErrorStatusCode.FORBIDDEN));
    }
}
