package com.xht.cloud.framework.log;

import com.xht.cloud.admin.api.log.feign.OperationLogClient;
import com.xht.cloud.framework.core.constant.SpringPropertiesNameConstant;
import com.xht.cloud.framework.log.aspect.OperationLogAspect;
import com.xht.cloud.framework.log.filter.LogHttpFilter;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 描述 ：日志自动注入
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    /**
     * 日志 过滤器
     */
    @Bean
    public FilterRegistrationBean<LogHttpFilter> logHttpFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogHttpFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LogHttpFilter());
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }

    @Bean
    @ConditionalOnBean(value = OperationLogClient.class)
    public OperationLogAspect operationLogAspect(@Value(value = SpringPropertiesNameConstant.SPRING_APPLICATION_KEY_SPEL) String applicationName, OperationLogClient operationLogClient) {
        return new OperationLogAspect(applicationName, operationLogClient);
    }
}
