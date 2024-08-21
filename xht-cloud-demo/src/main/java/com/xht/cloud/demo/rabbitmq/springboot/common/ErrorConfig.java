package com.xht.cloud.demo.rabbitmq.springboot.common;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.*;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
//@Configuration
public class ErrorConfig {

    /**
     * 重试耗尽后，返回`nack`，消息重新入队
     */
    @Bean
    public MessageRecoverer immediateRequeueMessageRecoverer() {
        return new ImmediateRequeueMessageRecoverer();
    }

    /**
     * 重试耗尽后，直接`reject`，丢弃消息。默认就是这种方式
     */
    @Bean
    public MessageRecoverer rejectAndDontRequeueRecoverer() {
        return new RejectAndDontRequeueRecoverer();
    }

    /**
     * 重试耗尽后，将失败消息投递到指定的交换机
     */
    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.exchange", "error.router.key");
    }

    @Bean
    public DirectExchange errorMessageExchange() {
        return new DirectExchange("error.exchange");
    }

    @Bean
    public Queue errorQueue() {
        return new Queue("error.queue", true);
    }

    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorMessageExchange) {
        return BindingBuilder.bind(errorQueue).to(errorMessageExchange).with("error.router.key");
    }

    /**
     * 批量处理
     */
    @Bean
    public MessageRecoverer messageBatchRecoverer() {
        return new MessageBatchRecoverer() {
            @Override
            public void recover(List<Message> messages, Throwable cause) {
                //自定进行其他的业务操作
                System.out.println("-------------------------");
            }
        };
    }
}
