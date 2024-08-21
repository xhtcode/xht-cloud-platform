package com.xht.cloud.demo.rabbitmq.expired;

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
public class ExpiredConfig {

    @Bean
    public DirectExchange getTimeExchange(){
        return new DirectExchange("time.out.exchange");
    }

    @Bean
    public Queue getTimeQueue(){
        return QueueBuilder.durable("time.out.queue").ttl(5000).build();
    }

    @Bean
    public Binding getTimeBinding(){
        return BindingBuilder.bind(getTimeQueue()).to(getTimeExchange()).with("time.out.queue");
    }



}
