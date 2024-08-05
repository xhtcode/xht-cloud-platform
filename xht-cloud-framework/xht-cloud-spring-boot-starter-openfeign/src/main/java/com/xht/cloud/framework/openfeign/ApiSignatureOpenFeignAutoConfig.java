package com.xht.cloud.framework.openfeign;

import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.openfeign.client.ApiSignatureClient;
import com.xht.cloud.framework.openfeign.filter.ApiSignatureOpenFeignFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 描述 ： 签名认证 配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(ApiSignatureProperties.class)
@ConditionalOnProperty(value = "xht.cloud.web.signature.type", havingValue = "rpc", matchIfMissing = true)
@EnableFeignClients(clients = ApiSignatureClient.class)
public class ApiSignatureOpenFeignAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "xht.cloud.web.signature.enabled", havingValue = "true", matchIfMissing = true)
    public ApiSignatureFilter apiSignatureOpenFeignFilter(ApiSignatureProperties apiSignatureProperties, ApiSignatureClient apiSignatureClient) {
        return new ApiSignatureOpenFeignFilter(apiSignatureProperties, apiSignatureClient);
    }
}
