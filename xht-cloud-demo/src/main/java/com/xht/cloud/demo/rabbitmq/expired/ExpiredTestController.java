package com.xht.cloud.demo.rabbitmq.expired;

import com.xht.cloud.framework.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/rabbitmq/expired")
public class ExpiredTestController {

    @Autowired
private RabbitTemplate rabbitTemplate;

    @GetMapping("/queue")
    public R<Void> test() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("time.out.exchange", "time.out.queue", "测试过期信息队列");
        }
        return R.ok();
    }

    @GetMapping("/message")
    public void testSendMessageTTL() {
        // 1、创建消息后置处理器对象
        MessagePostProcessor messagePostProcessor = (Message message) -> {
            // 设定 TTL 时间，以毫秒为单位
            message.getMessageProperties().setExpiration("5000");
            return message;
        };
        // 2、发送消息
        rabbitTemplate.convertAndSend(
                "time.out.exchange",
                "time.out.queue",
                "测试过期信息队列", messagePostProcessor);
    }
}
