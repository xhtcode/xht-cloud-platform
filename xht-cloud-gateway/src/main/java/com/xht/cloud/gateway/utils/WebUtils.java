package com.xht.cloud.gateway.utils;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.jackson.JsonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class WebUtils {


    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result   响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, R<?> result) {
        return webFluxResponseWriter(response, HttpStatus.OK, result);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result   响应状态码
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus httpStatus, R<?> result) {
        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, httpStatus, result);
    }


    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param result      响应信息
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, R<?> result) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtils.toJsonString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
