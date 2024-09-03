package com.xht.cloud.framework.web;

import com.xht.cloud.framework.web.convert.IEnumsIntegerConverterFactory;
import com.xht.cloud.framework.web.convert.IEnumsStringConverterFactory;
import com.xht.cloud.framework.web.handler.DefaultGlobalExceptionHandler;
import com.xht.cloud.framework.web.interceptor.SensitiveFieldHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述 ：web组件自动装配
 *
 * @author 小糊涂
 **/
@Slf4j
@ComponentScan("com.xht.cloud.framework.web.controller")
@AutoConfiguration
public class WebAutoConfiguration  implements WebMvcConfigurer {

    public WebAutoConfiguration() {
        log.debug(">>>>>>web-start web自动装配<<<<<<");
    }

    @Bean
    public DefaultGlobalExceptionHandler congoMallGlobalExceptionHandler() {
        return new DefaultGlobalExceptionHandler();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IEnumsStringConverterFactory());
        registry.addConverterFactory(new IEnumsIntegerConverterFactory());
    }


    /**
     * 添加Spring MVC生命周期拦截器，用于预处理和后处理 控制器方法调用和资源处理程序请求。
     * 拦截器可以注册为适用于所有请求或有限到URL模式的子集。
     *
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SensitiveFieldHandlerInterceptor())
                .addPathPatterns("/**");
    }
}
