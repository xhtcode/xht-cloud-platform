package com.xht.cloud.framework.openfeign.interceptor;

import com.xht.cloud.framework.utils.trace.TraceIdUtils;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.openfeign.core.OpenFeignProperties;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.web.HttpServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.xht.cloud.framework.constant.LogConstant.*;
import static com.xht.cloud.framework.core.rpc.RpcConstants.AUTHORIZATION;
import static com.xht.cloud.framework.core.rpc.RpcConstants.RPC_HEADER_KEY;

/**
 * 描述 ：openFeign 拦截器配置
 *
 * @author 小糊涂
 **/
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    private final OpenFeignProperties rpcHeaderValue;

    public FeignRequestInterceptor(OpenFeignProperties rpcHeaderValue) {
        this.rpcHeaderValue = rpcHeaderValue;
        log.debug(">>>>>>openfeign-start 自定义拦截器 <<<<<<");
    }

    public void apply(RequestTemplate template) {
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (Objects.isNull(request)) return;
        String headerValue = request.getHeader(RPC_HEADER_KEY);
        if (StringUtils.hasText(headerValue)) throw new BizException(GlobalErrorStatusCode.NOT_FOUND);
        String authorization = request.getHeader(AUTHORIZATION);
        String traceId = request.getHeader(REQUEST_TRACE_ID);
        String userId = request.getHeader(REQUEST_USER_ID);
        String username = request.getHeader(REQUEST_USER_ACCOUNT);
        if (StringUtils.hasText(authorization)) {
            template.header(AUTHORIZATION, authorization);
        }
        template.header(REQUEST_TRACE_ID, StringUtils.emptyToDefault(traceId, TraceIdUtils.getTraceId()));
        template.header(REQUEST_USER_ID, userId);
        template.header(REQUEST_USER_ACCOUNT, username);
        template.header(RPC_HEADER_KEY, rpcHeaderValue.getAuthValue());
        log.info("OpenFeign分布式调用，链路ID：{}，用户ID：{}，用户账号：{}，令牌：{}", traceId,
                userId, username,
                authorization);
    }
}
