package com.xht.cloud.framework.log.aspect;

import com.xht.cloud.framework.log.annotation.MethodTimeLogAnnotation;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * 描述 ：aop 方法计时 配置类
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
@Component
public class MethodTimeLogAspect {

    /**
     * 定义切面需要使用的注释
     */
    @Pointcut("@annotation(com.xht.cloud.framework.log.annotation.MethodTimeLogAnnotation)")
    public void point() {
    }

    /**
     * 方法计时 切点
     *
     * @param joinPoint               {@link ProceedingJoinPoint}
     * @param methodTimeLogAnnotation {@link MethodTimeLogAnnotation}
     * @return Object
     */
    @Around(value = "point() && @annotation(methodTimeLogAnnotation)")
    public Object doAround(ProceedingJoinPoint joinPoint, MethodTimeLogAnnotation methodTimeLogAnnotation) throws Throwable {
        Instant start = Instant.now();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = StringUtils.emptyToDefault(methodTimeLogAnnotation.value(), String.format("%s()", signature.getMethod().getName()));
        log.info("{}【方法计时】开始......", methodName);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            long millis = between.toMillis();
            log.error("{}【方法计时】异常  执行耗时: {}ms", methodName, millis, e);
            throw e;
        } finally {
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            long millis = between.toMillis();
            log.info("{}【方法计时】结束  执行耗时: {}ms", methodName, millis);
        }
    }

}
