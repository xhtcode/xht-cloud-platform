package com.xht.cloud.framework.security.resource.aspect;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.security.core.SecurityConfigProperties;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.lang.reflect.Method;
import java.util.Objects;

import static com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode.FORBIDDEN;
import static com.xht.cloud.framework.security.constant.SkipAuthTypeEnums.INNER;

/**
 * 描述 ：校验外放请求是否合法
 * <a href="https://www.yuque.com/pig4cloud/pig/fnxmvq">参考</a>
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
public class SkipAuthAspect {

    @Lazy
    @Autowired
    private SecurityConfigProperties oauth2Properties;

    @Before("@annotation(com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication)")
    public void around(JoinPoint joinPoint) throws BizException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SkipAuthentication skipAuthentication = method.getAnnotation(SkipAuthentication.class);
        if (Objects.nonNull(skipAuthentication)) {
            HttpServletRequest request = HttpServletUtils.getRequest();
            if (Objects.isNull(request)){
                throw new SysException(GlobalErrorStatusCode.BAD_REQUEST);
            }
            String securityHeaderValue = oauth2Properties.getSecurityHeaderValue();
            String requestHeaderValue = request.getHeader(skipAuthentication.headerKey());
            if (Objects.equals(INNER, skipAuthentication.value()) && !Objects.equals(securityHeaderValue, requestHeaderValue)) {
                log.error("外部访问接口 {} 没有权限 requestHeaderValue:{}", joinPoint.getSignature().getName(), requestHeaderValue);
                Assert.hasText(securityHeaderValue, () -> new SysException(GlobalErrorStatusCode.ERROR_CONFIGURATION));
                throw new BizException(FORBIDDEN);
            }
        }
    }


}
