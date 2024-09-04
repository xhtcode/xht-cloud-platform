package com.xht.cloud.framework.redis.idempotent.core.param;

import com.xht.cloud.framework.redis.exception.IdempotentException;
import com.xht.cloud.framework.redis.idempotent.IdempotentProperties;
import com.xht.cloud.framework.redis.idempotent.core.AbstractIdempotentTemplate;
import com.xht.cloud.framework.redis.idempotent.core.IdempotentContext;
import com.xht.cloud.framework.redis.idempotent.core.domain.IdempotentParamWrapper;
import com.xht.cloud.framework.jackson.JsonUtils;
import com.xht.cloud.framework.utils.secret.MD5Utils;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 描述 ：基于方法参数验证请求幂等性 实现类
 *
 * @author 小糊涂
 **/
public class IdempotentParamExecuteHandler extends AbstractIdempotentTemplate implements IdempotentParamService {
    private final RedissonClient redissonClient;
    private final static String LOCK = "lock:param:restAPI";

    public IdempotentParamExecuteHandler(IdempotentProperties idempotentProperties, RedissonClient redissonClient) {
        super(idempotentProperties);
        this.redissonClient = redissonClient;
    }

    /**
     * 幂等处理逻辑
     *
     * @param wrapper 幂等参数包装器
     */
    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        String lockKey = wrapper.getLockKey(String.format("path:%s:currentUserId:%s:md5:%s", getServletPath(), getCurrentUserId(), calcArgsMD5(wrapper.getJoinPoint())));
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.tryLock()) {
            throw new IdempotentException(wrapper.getIdempotent().message());
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

    /**
     * @return 获取当前线程上下文 ServletPath
     */
    private String getServletPath() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        assert request != null;
        return request.getServletPath();
    }

    /**
     * @return 当前操作用户 ID
     */
    private String getCurrentUserId() {
        return null;
    }

    /**
     * @return joinPoint md5
     */
    private String calcArgsMD5(ProceedingJoinPoint joinPoint) {
        return MD5Utils.generateSignature(JsonUtils.toJsonString(joinPoint.getArgs()));
    }
}
