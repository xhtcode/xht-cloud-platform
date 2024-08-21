package com.xht.cloud.demo.rabbitmq.dead;

import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
public class DeadListener {

    /**
     * 死信队列监听
     */
    @RabbitListener(queues = {DeadConfig.QUEUE_DEAD_LETTER})
    public void processMessageDead(String dataString, Message message, Channel channel) throws IOException {
        // 监听死信队列
        log.info("★[dead letter] 当前时间：{} 我是死信监听方法，我接收到了死信消息 :{}", DateUtil.now(), dataString);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 死信常规监听
     */
    // @RabbitListener(queues = {DeadConfig.QUEUE_NORMAL})
    public void processMessageNormal(Message message, Channel channel) throws IOException {
        // 监听正常队列，但是拒绝消息
        log.info("★[normal]消息接收到 {}", new String(message.getBody(), StandardCharsets.UTF_8));
        // log.info("★[normal]消息接收到，但我拒绝。");
        //  channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

}
