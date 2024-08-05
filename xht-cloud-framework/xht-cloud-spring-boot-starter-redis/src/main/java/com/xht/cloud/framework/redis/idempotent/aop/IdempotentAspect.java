package com.xht.cloud.framework.redis.idempotent.aop;

import com.xht.cloud.framework.redis.idempotent.annotation.Idempotent;
import com.xht.cloud.framework.redis.idempotent.core.IdempotentContext;
import com.xht.cloud.framework.redis.idempotent.core.IdempotentExecuteHandler;
import com.xht.cloud.framework.redis.idempotent.core.factory.IdempotentExecuteHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 描述 ：幂等注解 AOP 拦截器
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
public class IdempotentAspect {

    @Around("@within(com.xht.cloud.framework.redis.idempotent.annotation.Idempotent) && @annotation(idempotent)")
    public Object before(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        IdempotentExecuteHandler instance = IdempotentExecuteHandlerFactory.getInstance(idempotent.type());
        try {
            instance.execute(joinPoint, idempotent);
            return joinPoint.proceed();
        } finally {
            instance.postProcessing();
            IdempotentContext.clean();
        }
    }
}
