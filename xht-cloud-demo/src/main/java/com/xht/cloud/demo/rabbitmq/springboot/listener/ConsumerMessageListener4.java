package com.xht.cloud.demo.rabbitmq.springboot.listener;

import cn.hutool.core.thread.ThreadUtil;
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
public class ConsumerMessageListener4 {

    public static final String EXCHANGE_NAME = "boot.exchange.limit.direct";
    public static final String QUEUE_NAME = "boot.queue.limit";

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME),
            key = {"order"}
    )
    )
    public void processMessage(String dateString, Message message, Channel channel) {
        log.info("dateString：{}", dateString);
        log.info("message:{}", message);
        log.info("channel:{}", channel);
        ThreadUtil.sleep(1000);
    }
}
