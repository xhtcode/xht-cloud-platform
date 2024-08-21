package com.xht.cloud.demo.rabbitmq.springboot;

import cn.hutool.core.util.IdUtil;
import com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener2;
import com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener3;
import com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener4;
import com.xht.cloud.demo.rabbitmq.springboot.message_reliability.RabbitTemplateAckConfig;
import com.xht.cloud.framework.core.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/rabbitmq/test")
public class RabbitMQTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * {@link com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener}
     */
    @GetMapping("/send")
    public R<String> testSendMessage(@RequestParam(value = "message", required = false, defaultValue = "默认信息") String message) {
        rabbitTemplate.convertAndSend(
                "boot.exchange.direct.order",
                "order",
                message);
        return R.ok(message);
    }

    /**
     * {@link RabbitTemplateAckConfig}
     */
    @GetMapping("/message_reliability")
    public R<Void> message_reliability(
            @RequestParam(value = "exchange", required = false, defaultValue = "boot.exchange.direct.order") String exchange,
            @RequestParam(value = "routingKey", required = false, defaultValue = "test_routingKey") String routingKey
    ) {
        // 全局唯一的消息ID，需要封装到CorrelationData中
        CorrelationData correlationData = new CorrelationData(IdUtil.nanoId());
        correlationData.getFuture().thenAccept(confirm -> {
            if (confirm.isAck()) {
                // 3.1.ack，消息成功
                log.debug("getFuture() 消息发送成功, ID:{}", correlationData.getId());
            } else {
                // 3.2.nack，消息失败
                log.error("getFuture() 消息发送失败, ID:{}, 原因{}", correlationData.getId(), confirm.getReason());
            }
        });
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                "测试信息", correlationData);
        return R.ok();
    }

    /**
     * {@link com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener2}
     */

    @GetMapping("/consumer")
    public R<String> testConsumer(@RequestParam(value = "routingKey") String routingKey) {
        String message = String.format("订单号:%s", IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(
                ConsumerMessageListener2.EXCHANGE_NAME,
                routingKey,
                message);
        return R.ok(message);
    }

    /**
     * {@link com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener3}
     */
    @GetMapping("/act")
    public R<String> testAct() {
        String message = String.format("订单号:%s", IdUtil.simpleUUID());
        rabbitTemplate.convertAndSend(
                ConsumerMessageListener3.EXCHANGE_NAME,
                "order",
                message);
        return R.ok(message);
    }


    /**
     * {@link com.xht.cloud.demo.rabbitmq.springboot.listener.ConsumerMessageListener4}
     */
    @GetMapping("/limit")
    public R<String> testLimit() {
        String message = String.format("订单号:%s", IdUtil.simpleUUID());
        for (int i = 0; i < 1000; i++) {
            rabbitTemplate.convertAndSend(
                    ConsumerMessageListener4.EXCHANGE_NAME,
                    "order",
                    message);
        }
        return R.ok(message);
    }
}
