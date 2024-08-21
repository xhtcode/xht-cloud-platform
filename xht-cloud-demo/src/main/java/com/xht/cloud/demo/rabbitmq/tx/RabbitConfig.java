package com.xht.cloud.demo.rabbitmq.tx;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTransactionManager transactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public Exchange getTxExchange() {
        return new DirectExchange("tx_exchange");
    }


    @Bean
    public Queue getTxQueue() {
        return new Queue("tx_queue");
    }

    @Bean
    public Binding getTxBinding() {
        return BindingBuilder.bind(getTxQueue()).to(getTxExchange()).with("tx_routing_key").noargs();
    }


}