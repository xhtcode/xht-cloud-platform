package com.xht.cloud.framework.rabbitmq.core;

import com.xht.cloud.framework.core.config.CommonConfigProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：mongodb 配置类
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.rabbitmq")
public class RabbitMqProperties extends CommonConfigProperties {
}
