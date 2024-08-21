package com.xht.cloud.demo.rabbitmq.springboot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
public class ConsumerMessageListener {

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "queue.order", durable = "true"),
            exchange = @Exchange(value = "boot.exchange.direct.order"),
            key = {"order"}
    )
    )
    public void processMessage(String dateString, Message message, Channel channel) {
        log.info("dateString：{}", dateString);
        log.info("message:{}", message);
        log.info("channel:{}", channel);
    }
}