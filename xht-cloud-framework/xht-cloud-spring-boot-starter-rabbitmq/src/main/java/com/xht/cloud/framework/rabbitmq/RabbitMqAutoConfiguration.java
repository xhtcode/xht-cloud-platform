package com.xht.cloud.framework.rabbitmq;

import com.xht.cloud.framework.rabbitmq.core.RabbitMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 描述 ：mongodb 自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMqAutoConfiguration {
}
