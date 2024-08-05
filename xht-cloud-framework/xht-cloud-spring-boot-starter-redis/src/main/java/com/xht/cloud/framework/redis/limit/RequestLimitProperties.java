package com.xht.cloud.framework.redis.limit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.safety.limit")
public class RequestLimitProperties {

    /**
     * 是否关闭配置模式  关闭 false 打开  true 默认 true
     */
    private Boolean enabled = true;

    /**
     * redis 中key值的前缀
     */
    private final String prefix = "safety:limit";
}
