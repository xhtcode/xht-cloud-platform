package com.xht.cloud.framework.web.interceptor;

import com.xht.cloud.framework.jackson.desensitization.SkipSensitiveThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 描述 ：脱敏拦截器
 *
 * @author : 小糊涂
 **/
@Slf4j
public class SensitiveFieldHandlerInterceptor implements HandlerInterceptor {
    public SensitiveFieldHandlerInterceptor() {
        log.debug(">>>>>>web-start web 脱敏拦截器<<<<<<");
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        SkipSensitiveThreadLocal.remove();
    }

}
