package com.xht.cloud.demo.rabbitmq.tx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class TxRabbitMqListener {


    @RabbitListener(queues = "tx_queue")
    public void listener(Message message) {
        log.info("收到信息 {}", new String(message.getBody()));
    }
}
