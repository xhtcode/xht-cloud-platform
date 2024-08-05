package com.xht.cloud.framework.redis.idempotent.core;

import com.xht.cloud.framework.redis.idempotent.IdempotentProperties;
import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.redis.idempotent.core.domain.IdempotentParamWrapper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 描述 ：抽象幂等执行处理器
 *
 * @author 小糊涂
 **/
@AllArgsConstructor
public abstract class AbstractIdempotentTemplate implements IdempotentExecuteHandler {

    protected final IdempotentProperties idempotentProperties;

    /**
     * 执行幂等处理逻辑
     *
     * @param joinPoint  AOP 方法处理
     * @param idempotent 幂等注解
     */
    @Override
    public void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent) {
        // 模板方法模式：构建幂等参数包装器
        IdempotentParamWrapper idempotentParamWrapper = IdempotentParamWrapper.builder().joinPoint(joinPoint)
                .keyPrefix(idempotentProperties.getPrefix())
                .idempotent(idempotent).build();
        handler(idempotentParamWrapper);
    }
}
