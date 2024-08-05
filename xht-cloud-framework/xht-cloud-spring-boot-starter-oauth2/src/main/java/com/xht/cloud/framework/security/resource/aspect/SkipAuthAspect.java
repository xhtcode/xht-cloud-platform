package com.xht.cloud.framework.security.resource.aspect;

import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.security.constant.SkipAuthTypeEnums;
import com.xht.cloud.framework.security.core.SecurityConfigProperties;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Objects;

/**
 * 描述 ：校验外放请求是否合法
 * <a href="https://www.yuque.com/pig4cloud/pig/fnxmvq">参考</a>
 *
 * @author 小糊涂
 **/
@Slf4j
@Aspect
@RequiredArgsConstructor
public class SkipAuthAspect {

    private final SecurityConfigProperties oauth2Properties;

    @Before("@within(skipAuthentication) || @annotation(skipAuthentication)")
    public void before(JoinPoint joinPoint, SkipAuthentication skipAuthentication) {
        if (skipAuthentication == null) {
            Class<?> clazz = joinPoint.getTarget().getClass();
            skipAuthentication = AnnotationUtils.findAnnotation(clazz, SkipAuthentication.class);
        }
        if (Objects.nonNull(skipAuthentication)) {
            HttpServletRequest request = HttpServletUtils.getRequest();
            if (Objects.isNull(request)){
                throw new SysException(GlobalErrorStatusCode.BAD_REQUEST);
            }
            String securityHeaderValue = oauth2Properties.getSecurityHeaderValue();
            String requestHeaderValue = request.getHeader(skipAuthentication.headerKey());
            if (Objects.equals(SkipAuthTypeEnums.OUTER, skipAuthentication.value()) &&
                    !Objects.equals(securityHeaderValue, requestHeaderValue)) {
                log.error("外部访问接口 {} 没有权限 requestHeaderValue:{}", joinPoint.getSignature().getName(), requestHeaderValue);
                throw new BizException(GlobalErrorStatusCode.FORBIDDEN);
            }
        }
    }


}
