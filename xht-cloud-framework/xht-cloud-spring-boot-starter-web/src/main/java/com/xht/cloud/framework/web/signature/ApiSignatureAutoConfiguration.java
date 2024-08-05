package com.xht.cloud.framework.web.signature;

import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.web.signature.chain.ApiSignatureChain;
import com.xht.cloud.framework.web.signature.filter.ApiSignatureAuthenticationFilter;
import com.xht.cloud.framework.web.signature.filter.ApiSignaturePropertiesFilter;
import com.xht.cloud.framework.web.signature.filter.ApiSignatureVerifyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 ：api 接口签名 自动装配
 *
 * @author 小糊涂
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(ApiSignatureProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(value = "xht.cloud.web.signature.enabled", havingValue = "true", matchIfMissing = true)
public class ApiSignatureAutoConfiguration {

    public ApiSignatureAutoConfiguration() {
        log.debug(">>>>>>web-start api签名校验自动配置<<<<<<");
    }

    /**
     * 责任链配置
     */
    @Bean
    public ApiSignatureChain apiSignatureChain() {
        return new ApiSignatureChain();
    }

    /**
     * 配置切面
     */
    @Bean
    public ApiSignatureAspect apiSignatureAspect() {
        return new ApiSignatureAspect();
    }


    /**
     * 配置责任链-加密
     */
    @Bean(name = "apiSignatureEncryptFilter")
    public ApiSignatureFilter apiSignatureBaseFilter(ApiSignatureProperties apiSignatureProperties) {
        return new ApiSignatureVerifyFilter(apiSignatureProperties);
    }

    /**
     * 配置责任链-properties认证
     */
    @Bean(name = "apiSignaturePropertiesFilter")
    @ConditionalOnProperty(value = "xht.cloud.web.signature.type", havingValue = "properties", matchIfMissing = true)
    public ApiSignatureFilter apiSignaturePropertiesFilter(ApiSignatureProperties apiSignatureProperties) {
        return new ApiSignaturePropertiesFilter(apiSignatureProperties);
    }

    /**
     * 配置责任链-最终签名认证
     */
    @Bean(name = "apiSignatureAuthenticationFilter")
    public ApiSignatureFilter apiSignatureAuthenticationFilter() {
        return new ApiSignatureAuthenticationFilter();
    }

}
