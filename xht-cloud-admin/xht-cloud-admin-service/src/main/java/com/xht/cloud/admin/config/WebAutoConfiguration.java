package com.xht.cloud.admin.config;

import com.xht.cloud.admin.enums.DeptStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述 ：web组件自动装配
 *
 * @author 小糊涂
 **/
@Slf4j
@Configuration
public class WebAutoConfiguration implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {

    }
}
