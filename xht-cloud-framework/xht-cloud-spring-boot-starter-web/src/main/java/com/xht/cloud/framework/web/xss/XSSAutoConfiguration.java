package com.xht.cloud.framework.web.xss;

import com.xht.cloud.framework.web.xss.filter.XSSFilter;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述 ：xss 防范配置类
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(XSSProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(value = "xht.cloud.safety.xss.enable", havingValue = "true", matchIfMissing = true)
public class XSSAutoConfiguration implements WebMvcConfigurer {

    /**
     * 创建 XssFilter Bean，解决 Xss 安全问题
     */
    @Bean
    @ConditionalOnBean(PathMatcher.class)
    public FilterRegistrationBean<XSSFilter> xssFilter(XSSProperties properties, PathMatcher pathMatcher) {
        FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XSSFilter(properties, pathMatcher));
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }

}
