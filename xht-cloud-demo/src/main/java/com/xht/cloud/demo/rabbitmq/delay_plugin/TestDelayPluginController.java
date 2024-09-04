package com.xht.cloud.demo.rabbitmq.delay_plugin;

import cn.hutool.core.date.DateUtil;
import com.xht.cloud.framework.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
@RequestMapping("/rabbitmq/delay_plugin")
@RequiredArgsConstructor
public class TestDelayPluginController {
private final RabbitTemplate rabbitTemplate;


    @GetMapping("/push1")
    public R<Void> send() {
        rabbitTemplate
                .convertAndSend(
                        "delay.direct1",
                        "delay1",
                        "发送时间：" + DateUtil.now()
                        , new MessagePostProcessor() {
                            @Override
                            public Message postProcessMessage(Message message) throws AmqpException {
                                // 添加延迟消息属性
                                message.getMessageProperties().setDelayLong(5000L);
//                                message.getMessageProperties().setHeader("x-delay", 5000L);;
                                return message;
                            }
                        });
        return R.ok();
    }
}
