package com.xht.cloud.demo.rabbitmq.dead;

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
public class DeadConfig {

    //-----------------------------死信配置-----------------------------------
    public static final String EXCHANGE_DEAD_LETTER = "dead.exchange";
    public static final String QUEUE_DEAD_LETTER = "dead.queue";
    public static final String ROUTING_KEY_DEAD_LETTER = "dead.exchange.queue";

    //-----------------------------常规配置-----------------------------------
    public static final String EXCHANGE_NORMAL = "normal.exchange";
    public static final String QUEUE_NORMAL = "normal.queue";
    public static final String ROUTING_KEY_NORMAL = "normal.exchange.queue";

    /**
     * 设置死信交换机
     */
    @Bean
    public DirectExchange getDeadExchange() {
        return new DirectExchange(EXCHANGE_DEAD_LETTER);
    }

    /**
     * 设置死信队列
     */
    @Bean
    public Queue getDeadQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_LETTER).build();
    }

    /**
     * 死信交换机和死信队列绑定
     */
    @Bean
    public Binding getDeadBinding() {
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(ROUTING_KEY_DEAD_LETTER);
    }

    /**
     * 设置常规的交换机
     */
    @Bean
    public DirectExchange getNormalExchange() {
        return new DirectExchange(EXCHANGE_NORMAL);
    }

    /**
     * 设置常规的队列
     */
    @Bean
    public Queue getNormalQueue() {
        return QueueBuilder.durable(QUEUE_NORMAL)
                .deadLetterExchange(EXCHANGE_DEAD_LETTER) // 对应的死信交换机
                .deadLetterRoutingKey(ROUTING_KEY_DEAD_LETTER) // 对应的死信路由键
                .maxLength(10L) //设置队列最大容量
                .ttl(10000) //设置消息超时时间 10 秒
                .build();
    }

    /**
     * 设置常规的交换机和常规的队列绑定
     */
    @Bean
    public Binding getNormalBinding() {
        return BindingBuilder.bind(getNormalQueue()).to(getNormalExchange()).with(ROUTING_KEY_NORMAL);
    }
}
