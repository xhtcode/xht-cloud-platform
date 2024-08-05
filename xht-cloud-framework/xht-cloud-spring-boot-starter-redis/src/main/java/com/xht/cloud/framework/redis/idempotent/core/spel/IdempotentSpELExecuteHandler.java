package com.xht.cloud.framework.redis.idempotent.core.spel;

import com.xht.cloud.framework.redis.idempotent.IdempotentProperties;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.redis.idempotent.core.AbstractIdempotentTemplate;
import com.xht.cloud.framework.redis.idempotent.core.IdempotentContext;
import com.xht.cloud.framework.redis.idempotent.core.domain.IdempotentParamWrapper;
import com.xht.cloud.framework.utils.spring.SpELUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 描述 ：基于 SpEL 方法验证请求幂等性，适用于 RestAPI 场景
 *
 * @author 小糊涂
 **/
@Slf4j
public class IdempotentSpELExecuteHandler extends AbstractIdempotentTemplate implements IdempotentSpELService {
    private final RedissonClient redissonClient;

    private final static String LOCK = "lock:spEL:restAPI";

    public IdempotentSpELExecuteHandler(IdempotentProperties idempotentProperties, RedissonClient redissonClient) {
        super(idempotentProperties);
        this.redissonClient = redissonClient;
    }

    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        ProceedingJoinPoint joinPoint = wrapper.getJoinPoint();
        Idempotent idempotent = wrapper.getIdempotent();
        String key = (String) SpELUtil.parseKey(idempotent.expression(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        String lockKey = wrapper.getLockKey(key);
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.tryLock()) {
            return;
        }
        IdempotentContext.put(LOCK, lock);
    }

    @Override
    public void postProcessing() {
        RLock lock = null;
        try {
            lock = (RLock) IdempotentContext.getKey(LOCK);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
