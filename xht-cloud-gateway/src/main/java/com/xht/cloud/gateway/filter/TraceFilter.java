package com.xht.cloud.gateway.filter;

import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.utils.trace.TraceIdUtils;
import com.xht.cloud.framework.web.ServerHttpRequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.xht.cloud.framework.constant.LogConstant.*;

/**
 * 分布式请求链路过滤器.
 *
 * @author 小糊涂
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TraceFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String userId = ServerHttpRequestUtils.getParamValue(request, REQUEST_USER_ID);
        String userAccount = ServerHttpRequestUtils.getParamValue(request, REQUEST_USER_ACCOUNT);
        String traceId = ServerHttpRequestUtils.getParamValue(request, REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.generateTraceId();
        }
        // 获取uri
        String requestURL = ServerHttpRequestUtils.getRequestURL(request);
        log.info("请求路径：{}， 用户ID：{}， 用户账号：{}，链路ID：{}", requestURL, userId,
                userAccount,traceId);
        return chain.filter(exchange.mutate()
                .request(request.mutate()
                        .header(REQUEST_USER_ACCOUNT, userAccount)
                        .header(REQUEST_USER_ID, userId)
                        .header(REQUEST_TRACE_ID, traceId)
                        .build())
                .build());
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
