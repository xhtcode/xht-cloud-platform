package com.xht.cloud.framework.redis.idempotent.core.domain;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.redis.key.RedisKeyTool;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IdempotentParamWrapper {
    /**
     * 幂等注解
     */
    private final Idempotent idempotent;

    /**
     * AOP 处理连接点
     */
    private final ProceedingJoinPoint joinPoint;

    /**
     * 锁标识前缀
     */
    private final String keyPrefix;

    /**
     * 构建key
     */
    public final String getLockKey(String... key) {
        Assert.notEmpty(key, "构建 key 时不能为空 ");
        return RedisKeyTool.createKey(keyPrefix, RedisKeyTool.createKey(key));
    }
}
