package com.xht.cloud.demo.rabbitmq.tx;

import com.xht.cloud.framework.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/rabbitmq/tx")
@RequiredArgsConstructor
public class TxRabbitMqController {
private final RabbitTemplate rabbitTemplate;

    @GetMapping("/success")
    public R<Void> test1() {
        // 1、发送第一条消息
        rabbitTemplate.convertAndSend("tx_exchange", "tx_routing_key", "测试 tx ~~~01)");

        // 2、发送第二条消息
        rabbitTemplate.convertAndSend("tx_exchange", "tx_routing_key", "测试 tx ~~~02)");
        return R.ok();
    }

    @Transactional
    @GetMapping("/error")
    public R<Void> test2() {
        // 1、发送第一条消息
        rabbitTemplate.convertAndSend("tx_exchange", "tx_routing_key", "测试 tx ~~~01)");
        System.out.println(1 / 0);
        // 2、发送第二条消息
        rabbitTemplate.convertAndSend("tx_exchange", "tx_routing_key", "测试 tx ~~~02)");
        return R.ok();
    }
}
