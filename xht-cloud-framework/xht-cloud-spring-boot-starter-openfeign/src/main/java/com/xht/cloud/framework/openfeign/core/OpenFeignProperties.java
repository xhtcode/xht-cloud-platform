package com.xht.cloud.framework.openfeign.core;

import com.xht.cloud.framework.exception.Assert;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：自定义 OpenFeign 配置
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.feign")
public class OpenFeignProperties {

    private Header header = new Header();

    @Data
    static class Header {

        /**
         * 授权请求头 值
         */
        private String authValue = "123456";
    }

    public String getAuthValue() {
        Assert.notNull(header, "查询不到具体请求头配置");
        return header.getAuthValue();
    }
}
