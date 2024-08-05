package com.xht.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 描述 ：跨域配置
 *
 * @author 小糊涂
 **/
@Slf4j
@Component
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        // 获取request对象
        ServerHttpRequest request = serverWebExchange.getRequest();
        // 非跨域请求，直接放行
        if (!CorsUtils.isCorsRequest(request)) {
            return webFilterChain.filter(serverWebExchange);
        }
        // 获取请求头
        HttpHeaders requestHeaders = request.getHeaders();
        // 获取response对象
        ServerHttpResponse response = serverWebExchange.getResponse();
        // 获取请求头的请求方法
        HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
        // 获取响应头
        HttpHeaders responseHeaders = response.getHeaders();
        // 允许跨域
        responseHeaders.add("Access-Control-Allow-Origin", requestHeaders.getOrigin());
        // 允许请求头
        responseHeaders.addAll("Access-Control-Allow-Headers",
                requestHeaders.getAccessControlRequestHeaders());
        // 允许方法
        if (requestMethod != null) {
            responseHeaders.add("Access-Control-Allow-Methods", requestMethod.name());
        }
        // 允许证书
        responseHeaders.add("Access-Control-Allow-Credentials", "true");
        // 暴露响应头
        responseHeaders.add("Access-Control-Expose-Headers", CorsConfiguration.ALL);
        // 每1个小时发送一次预请求
        responseHeaders.add("Access-Control-Max-Age", "3600");
        // 获取方法
        HttpMethod method = request.getMethod();
        if (method == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        return webFilterChain.filter(serverWebExchange);
    }

}
