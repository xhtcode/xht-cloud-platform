package com.xht.cloud.demo.rabbitmq.dead;

import cn.hutool.core.date.DateUtil;
import com.xht.cloud.framework.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/rabbitmq/dead")
@RequiredArgsConstructor
public class DeadController {
private final RabbitTemplate rabbitTemplate;


    @GetMapping("/push1")
    public R<Void> send() {
        rabbitTemplate
                .convertAndSend(
                        DeadConfig.EXCHANGE_NORMAL,
                        DeadConfig.ROUTING_KEY_NORMAL,
                        "测试死信情况1：消息被拒绝");
        return R.ok();
    }

    @GetMapping("/push2")
    public R<Void> send2() {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate
                    .convertAndSend(
                            DeadConfig.EXCHANGE_NORMAL,
                            DeadConfig.ROUTING_KEY_NORMAL,
                            "测试死信情况:" + i);
        }
        return R.ok();
    }

    @GetMapping("/push3")
    public R<Void> push3() {
        rabbitTemplate
                .convertAndSend(
                        DeadConfig.EXCHANGE_NORMAL,
                        DeadConfig.ROUTING_KEY_NORMAL,
                        "测试死信情况3：" + DateUtil.now());
        return R.ok();
    }
}
