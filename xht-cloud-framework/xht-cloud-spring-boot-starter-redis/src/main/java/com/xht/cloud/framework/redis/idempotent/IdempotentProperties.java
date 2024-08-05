package com.xht.cloud.framework.redis.idempotent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.safety.repeat")
public class IdempotentProperties {

    /**
     * 是否关闭配置模式  关闭 false 打开  true 默认 true
     */
    private Boolean enabled = true;

    /**
     * redis 中key值的前缀
     */
    private final String prefix = "safety:idempotent";

    /**
     * Token 申请后过期时间
     * 单位默认毫秒 {@link TimeUnit#MILLISECONDS}
     */
    private Long timeout = 6000L;

}
