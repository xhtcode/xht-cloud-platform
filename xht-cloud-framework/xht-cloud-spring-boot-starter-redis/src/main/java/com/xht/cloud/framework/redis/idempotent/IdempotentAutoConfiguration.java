package com.xht.cloud.framework.redis.idempotent;

import com.xht.cloud.framework.redis.idempotent.aop.IdempotentAspect;
import com.xht.cloud.framework.redis.idempotent.core.param.IdempotentParamExecuteHandler;
import com.xht.cloud.framework.redis.idempotent.core.param.IdempotentParamService;
import com.xht.cloud.framework.redis.idempotent.core.spel.IdempotentSpELExecuteHandler;
import com.xht.cloud.framework.redis.idempotent.core.spel.IdempotentSpELService;
import com.xht.cloud.framework.redis.idempotent.core.token.IdempotentTokenController;
import com.xht.cloud.framework.redis.idempotent.core.token.IdempotentTokenExecuteHandler;
import com.xht.cloud.framework.redis.idempotent.core.token.IdempotentTokenService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 描述 ：重复提交
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(IdempotentProperties.class)
@ConditionalOnProperty(value = "xht.cloud.safety.limit.repeat", havingValue = "true", matchIfMissing = true)
public class IdempotentAutoConfiguration {

    public IdempotentAutoConfiguration() {
        log.info(">>>>>>redis-start 幂等<<<<<<");
    }

    /**
     * 幂等切面
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * 参数方式幂等实现，基于 RestAPI 场景
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentParamService idempotentParamExecuteHandler(IdempotentProperties idempotentProperties, RedissonClient redissonClient) {
        return new IdempotentParamExecuteHandler(idempotentProperties, redissonClient);
    }

    /**
     * Token 方式幂等实现，基于 RestAPI 场景
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentTokenService idempotentTokenExecuteHandler(IdempotentProperties idempotentProperties, RedisTemplate<String, Object> redisTemplate) {
        return new IdempotentTokenExecuteHandler(idempotentProperties, redisTemplate);
    }

    /**
     * 申请幂等 Token 控制器，基于 RestAPI 场景
     */
    @Bean
    public IdempotentTokenController idempotentTokenController(IdempotentTokenService idempotentTokenService) {
        return new IdempotentTokenController(idempotentTokenService);
    }

    /**
     * SpEL 方式幂等实现，
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentSpELService idempotentSpELByRestAPIExecuteHandler(IdempotentProperties idempotentProperties, RedissonClient redissonClient) {
        return new IdempotentSpELExecuteHandler(idempotentProperties, redissonClient);
    }

}
