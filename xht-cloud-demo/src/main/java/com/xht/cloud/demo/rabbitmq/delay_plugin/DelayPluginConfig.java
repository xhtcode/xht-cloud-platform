package com.xht.cloud.demo.rabbitmq.delay_plugin;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Configuration
public class DelayPluginConfig {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue1", durable = "true"),
            exchange = @Exchange(name = "delay.direct1", delayed = "true"),
            key = "delay1"
    ))
    public void listenDelayMessage(String msg) {
        log.info("接收到delay.queue1 时间： {} 的延迟消息：{} ", DateUtil.now(),msg);
    }

    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder
                .directExchange("delay.direct2") // 指定交换机类型和名称
                .delayed() // 设置delay的属性为true
                .durable(true) // 持久化
                .build();
    }

    @Bean
    public org.springframework.amqp.core.Queue delayedQueue() {
        return new org.springframework.amqp.core.Queue("delay.queue2");
    }

    @Bean
    public Binding delayQueueBinding() {
        return BindingBuilder.bind(delayedQueue()).to(delayExchange()).with("delay2");
    }

}
