package com.xht.cloud.demo.rabbitmq.springboot.message_reliability;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitTemplateAckConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }


    /**
     * 确认回调
     *
     * @param correlationData 回调的相关数据。
     * @param ack             ack为True, nack为false
     * @param cause           一个可选的原因，对于nack，当可用时，否则为空。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("confirm() 回调函数打印 CorrelationData：{}", correlationData);
        log.info("confirm() 回调函数打印 ack：{}", ack);
        log.info("confirm() 回调函数打印 cause：{}" , cause);
    }

    /**
     * 发送到队列失败时才调用这个方法。
     *
     * @param returned 返回的消息和元数据。
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("returnedMessage() 回调函数 消息主体: {}" , new String(returned.getMessage().getBody()));
        log.info("returnedMessage() 回调函数 应答码: {}" , returned.getReplyCode());
        log.info("returnedMessage() 回调函数 描述：{}" , returned.getReplyText());
        log.info("returnedMessage() 回调函数 消息使用的交换器 exchange : {}" , returned.getExchange());
        log.info("returnedMessage() 回调函数 消息使用的路由键 routing : {}" , returned.getRoutingKey());
    }
}
