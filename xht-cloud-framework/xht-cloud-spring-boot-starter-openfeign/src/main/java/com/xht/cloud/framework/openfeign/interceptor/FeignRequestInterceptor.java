package com.xht.cloud.framework.openfeign.interceptor;

import com.xht.cloud.framework.core.constant.LogConstant;
import com.xht.cloud.framework.core.rpc.RpcConstants;
import com.xht.cloud.framework.openfeign.core.OpenFeignProperties;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.core.trace.TraceIdUtils;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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
        template.header(RpcConstants.RPC_HEADER_KEY, rpcHeaderValue.getAuthValue());
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (Objects.isNull(request)) return;
        String authorization = request.getHeader(RpcConstants.AUTHORIZATION);
        String traceId = request.getHeader(LogConstant.TRACE_ID);
        String userId = request.getHeader(LogConstant.USER_ID);
        String username = request.getHeader(LogConstant.USER_ACCOUNT);
        if (StringUtils.hasText(authorization)) {
            template.header(RpcConstants.AUTHORIZATION, authorization);
        }
        template.header(LogConstant.TRACE_ID, StringUtils.emptyToDefault(traceId, TraceIdUtils.getTraceId()));
        template.header(LogConstant.USER_ID, userId);
        template.header(LogConstant.USER_ACCOUNT, username);
        log.info("OpenFeign分布式调用，链路ID：{}，用户ID：{}，用户账号：{}，令牌：{}", traceId,
                userId, username,
                authorization);
    }
}
