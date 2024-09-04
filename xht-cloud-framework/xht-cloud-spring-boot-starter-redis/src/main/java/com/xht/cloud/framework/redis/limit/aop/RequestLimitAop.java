package com.xht.cloud.framework.redis.limit.aop;

import com.xht.cloud.framework.redis.exception.RequestLimitException;
import com.xht.cloud.framework.redis.key.RedisKeyTool;
import com.xht.cloud.framework.redis.limit.RequestLimitProperties;
import com.xht.cloud.framework.redis.limit.annotation.RequestLimit;
import com.xht.cloud.framework.utils.ResourceUtils;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：描述 ：接口限流 默认一分钟10次
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
public class RequestLimitAop {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RequestLimitProperties requestLimitProperties;
    private final DefaultRedisScript<Long> redisScript;


    public RequestLimitAop(RedisTemplate<String, Object> redisTemplate, RequestLimitProperties requestLimitProperties) {
        this.redisTemplate = redisTemplate;
        this.requestLimitProperties = requestLimitProperties;
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(ResourceUtils.getResource("META-INF/scripts/rateLimiter.lua")));
        log.info(">>>>>>redis-start 接口限流<<<<<<");
    }

    @Pointcut("@annotation(com.xht.cloud.framework.redis.limit.annotation.RequestLimit)")
    private void check() {
    }

    @Before("check()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //拿到RedisLimit注解，如果存在则说明需要限流
        RequestLimit redisLimit = method.getAnnotation(RequestLimit.class);
        if (redisLimit != null) {
            //获取redis的key
            String key = redisLimit.key();
            String className = method.getDeclaringClass().getName();
            String name = method.getName();
            String limitKey = RedisKeyTool.createKey(requestLimitProperties.getPrefix(), key, className, method.getName());
            log.info("name is {} limitKey is {}", name, limitKey);
            if (!StringUtils.hasText(key)) {
                throw new RequestLimitException("key cannot be null");
            }
            long limit = redisLimit.limit();
            long expire = redisLimit.expire();
            List<String> keys = new ArrayList<>();
            keys.add(limitKey);
            Long count = redisTemplate.execute(redisScript, keys, limit, expire);
            log.info("Access try count is {} for key={}", count, key);
            if (count != null && count == 0) {
                log.debug("令牌桶={}，获取令牌失败", key);
                throw new RequestLimitException(redisLimit.msg());
            }
        }
    }

}
