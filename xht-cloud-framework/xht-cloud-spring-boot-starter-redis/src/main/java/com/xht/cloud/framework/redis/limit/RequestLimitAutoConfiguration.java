package com.xht.cloud.framework.redis.limit;

import com.xht.cloud.framework.redis.limit.aop.RequestLimitAop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 描述 ：接口限流
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(RequestLimitProperties.class)
@ConditionalOnProperty(value = "xht.cloud.safety.limit.enabled", havingValue = "true", matchIfMissing = true)
public class RequestLimitAutoConfiguration {

    @Bean
    public RequestLimitAop requestLimitAop(RequestLimitProperties requestLimitProperties, RedisTemplate<String, Object> redisTemplate) {
        return new RequestLimitAop(redisTemplate, requestLimitProperties);
    }

}
