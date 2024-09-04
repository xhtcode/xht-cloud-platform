package com.xht.cloud.gateway.handler;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.gateway.utils.WebUtils;
import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述 ：网关统一异常处理
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@NonNullApi
public class GatewayGlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable e) {
        ServerHttpResponse response = exchange.getResponse();
        if (e instanceof NotFoundException) {
            log.error("服务正在维护，请联系管理员，错误信息：{}，详情见日志", e.getMessage(), e);
            return WebUtils.webFluxResponseWriter(response, R.failed("服务正在维护"));
        }
        if (e instanceof ResponseStatusException responseStatusException) {
            int statusCode = responseStatusException.getStatusCode().value();
            log.error("状态码：{}, 错误信息：{}，详情见日志", statusCode, e.getMessage(), e);
        }
        return WebUtils.webFluxResponseWriter(response, R.failed());
    }
}
