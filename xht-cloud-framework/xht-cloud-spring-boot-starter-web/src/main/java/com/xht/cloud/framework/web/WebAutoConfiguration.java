package com.xht.cloud.framework.web;

import com.xht.cloud.framework.web.convert.IEnumsIntegerConverterFactory;
import com.xht.cloud.framework.web.convert.IEnumsStringConverterFactory;
import com.xht.cloud.framework.web.handler.DefaultGlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.FormatterRegistry;
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
}
