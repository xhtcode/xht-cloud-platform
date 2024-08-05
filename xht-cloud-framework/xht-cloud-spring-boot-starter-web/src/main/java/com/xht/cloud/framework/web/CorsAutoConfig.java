package com.xht.cloud.framework.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 描述 ：跨域配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
public class CorsAutoConfig {

    @Bean
    @ConditionalOnMissingBean(CorsFilter.class)
    @Order(HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有域名跨域
        config.addAllowedOriginPattern(CorsConfiguration.ALL);
        // 允许证书
        config.setAllowCredentials(true);
        // 允许所有方法
        config.addAllowedMethod(CorsConfiguration.ALL);
        // 允许任何头
        config.addAllowedHeader(CorsConfiguration.ALL);
        // 每一个小时，异步请求都发起预检请求 => 发送两次请求 第一次OPTION 第二次GET/POT/PUT/DELETE
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);
        log.debug(">>>>>>web-start cors跨域加载<<<<<<");
        return new CorsFilter(configurationSource);
    }

}
