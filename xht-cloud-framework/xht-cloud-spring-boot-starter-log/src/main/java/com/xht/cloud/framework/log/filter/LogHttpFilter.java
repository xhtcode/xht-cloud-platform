package com.xht.cloud.framework.log.filter;

import com.xht.cloud.framework.constant.LogConstant;
import com.xht.cloud.framework.core.rpc.RpcConstants;
import com.xht.cloud.framework.utils.trace.TraceIdUtils;
import com.xht.cloud.framework.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import java.io.IOException;

/**
 * 描述 ：日志 过滤器
 *
 * @author 小糊涂
 **/
@Slf4j
@Setter
public class LogHttpFilter extends HttpFilter implements Ordered {

    public LogHttpFilter() {
        log.debug(">>>>>>redis-start 日志统计<<<<<<");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorization = request.getHeader(RpcConstants.AUTHORIZATION);
        String traceId = request.getHeader(LogConstant.REQUEST_TRACE_ID);
        String userId = request.getHeader(LogConstant.REQUEST_USER_ID);
        String username = request.getHeader(LogConstant.REQUEST_USER_ACCOUNT);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.generateTraceId();
        }
        TraceIdUtils.putTraceId(traceId);
        try {
            chain.doFilter(request, response);
        } finally {
            log.debug("【日志 过滤器】 链路ID：{}， 用户ID：{}，用户账号：{}，令牌：{}",
                    traceId, userId, username,
                     authorization);
            TraceIdUtils.removeTraceId();
        }
    }

}
