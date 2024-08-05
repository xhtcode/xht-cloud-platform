package com.xht.cloud.framework.openfeign.client;

import com.xht.cloud.framework.core.domain.KeyValue;
import com.xht.cloud.framework.core.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述 ：
 * value/name：指定提供者的微服务名称
 * url：直接指定请求的路径地址
 * decode404：是否应该编码或者抛出FeignException异常
 * configuration：配置feign.codec.Decoder、feign.codec.Encoder、feign.Contract
 * fallback:指定发送异常调用或者超时时应该调用那个类来执行备用方法
 * fallbackFactory：提供统一的异常熔断处理，避免重复代码的编写
 * path：当服务提供者使用了server.context.path时。
 * contextId：用来唯一标识 当一个微服务中存在多个FeignClient接口调用同一个服务提供者时的场景(若是不提供该属性值，则在程序启动时会启动失败，并提示如下信息)
 *
 * @author 小糊涂
 **/
@FeignClient(name = "${xht.cloud.web.signature.serverName:jjj}",contextId = "apiSignatureClient")
public interface ApiSignatureClient {
    /**
     * 根据 应用的唯一标识查询
     */
    @RequestMapping("/api/signature/findAppId")
    R<KeyValue<String, String>> findAppId(String appId);
}
