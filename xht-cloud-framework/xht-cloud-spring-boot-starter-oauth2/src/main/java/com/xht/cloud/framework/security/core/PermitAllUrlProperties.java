package com.xht.cloud.framework.security.core;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述 ：资源服务器对外直接暴露URL,如果设置 contex-path(springboot请求统一前缀) 要特殊处理
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
@Getter
@RequiredArgsConstructor
public class PermitAllUrlProperties implements InitializingBean {

    private final SecurityConfigProperties securityConfigProperties;

    private final List<String> whiteUrls = new ArrayList<>();

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)}");


    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        whiteUrls.addAll(securityConfigProperties.formatWhiteUrls());
        handlerMethods.keySet().forEach(info -> {
            HandlerMethod handlerMethod = handlerMethods.get(info);
            // 获取方法上边的注解 替代path variable 为 *
            SkipAuthentication method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), SkipAuthentication.class);
            SkipAuthentication controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), SkipAuthentication.class);
            if (null != method && null != controller) {
                throw new SysException("禁止类、方法同时使用 " + SkipAuthentication.class.getName());
            } else if (null != method) {
                if (Objects.nonNull(info.getPathPatternsCondition())) {
                    initUrl(info.getPathPatternsCondition().getPatternValues());
                }
            } else if (null != controller) {
                if (Objects.nonNull(info.getPathPatternsCondition())) {
                    initUrl(info.getPathPatternsCondition().getPatternValues());
                }
            }
        });
        log.info("\n资源服务器对外直接暴露URL 全部:\033[1;33m\n---------> {}\033[0m", StrUtil.join("\n---------> ", whiteUrls));
    }

    private void initUrl(Set<String> url) {
        if (CollectionUtils.isEmpty(url)) {
            return;
        }
        ArrayList<String> resultUrl = url.stream().map(t -> ReUtil.replaceAll(t, PATTERN, "*")).collect(Collectors.toCollection(ArrayList::new));
        if (!CollectionUtils.isEmpty(resultUrl)) {
            whiteUrls.addAll(resultUrl);
        }
    }

}
