package com.xht.cloud.demo.rabbitmq.springboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Configuration
public class CommonConfig {

    @Bean
    public DirectExchange simpleDirect() {
        return new DirectExchange("simple.direct", true, false);
    }

    @Bean
    public Queue simpleQueue() {
        return QueueBuilder.durable("simple.queue").build();
    }

    @Bean
    public Binding simpleMessageBinding() {
        return BindingBuilder.bind(simpleQueue()).to(simpleDirect()).with("simple");
    }
}
