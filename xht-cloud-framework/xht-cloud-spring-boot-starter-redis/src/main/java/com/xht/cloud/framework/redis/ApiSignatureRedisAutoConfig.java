package com.xht.cloud.framework.redis;

import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.redis.filter.ApiSignatureNonceRepeatFilter;
import com.xht.cloud.framework.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 描述 ：接口验验签 redis 过滤器
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(ApiSignatureProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(value = "xht.cloud.web.signature.enabled", havingValue = "true", matchIfMissing = true)
public class ApiSignatureRedisAutoConfig {

    public ApiSignatureRedisAutoConfig() {
    }

    @Bean
    @ConditionalOnBean(RedisService.class)
    public ApiSignatureFilter apiSignatureNonceRepeatFilter(ApiSignatureProperties apiSignatureProperties, RedisService redisService) {
        return new ApiSignatureNonceRepeatFilter(apiSignatureProperties, redisService);
    }

}
