package com.xht.cloud.framework.web.interceptor;

import com.xht.cloud.framework.jackson.desensitization.SkipSensitiveThreadLocal;
import com.xht.cloud.framework.jackson.translation.SkipTransDictThreadLocal;
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
public class GlobalHandlerInterceptor implements HandlerInterceptor {
    public GlobalHandlerInterceptor() {
        log.debug(">>>>>>web-start web 自定义拦截器<<<<<<");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        SkipSensitiveThreadLocal.remove();
        SkipTransDictThreadLocal.remove();
    }

}
