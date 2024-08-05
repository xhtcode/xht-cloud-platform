package com.xht.cloud.gateway.handler;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.gateway.constant.GateWayErrorStatusCode;
import com.xht.cloud.gateway.utils.WebUtils;
import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
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
            return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.SERVICE_UNAVAILABLE));
        }
        if (e instanceof ResponseStatusException responseStatusException) {
            int statusCode = responseStatusException.getStatusCode().value();
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.NOT_FOUND));
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                log.error("状态码：{}，错误请求，错误信息：{}，详情见日志", statusCode, e.getMessage(), e);
                return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.BAD_REQUEST));
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.error("状态码：{}，服务器内部错误，无法完成请求，错误信息：{}，详情见日志", statusCode, e.getMessage(), e);
                return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.INTERNAL_SERVER_ERROR));
            }
        }
//        if (BlockException.isBlockException(e)) {
//            // 思路来源于SentinelGatewayBlockExceptionHandler
//            log.error("请求太频繁，错误信息：{}，详情见日志", LogUtil.record(e.getMessage()), e);
//            return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.TOO_MANY_REQUESTS));
//        }
        return WebUtils.webFluxResponseWriter(response, R.failed(GateWayErrorStatusCode.BAD_GATEWAY));
    }
}
