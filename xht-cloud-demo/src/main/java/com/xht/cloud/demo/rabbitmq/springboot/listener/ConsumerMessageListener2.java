package com.xht.cloud.demo.rabbitmq.springboot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
public class ConsumerMessageListener2 {

    public static final String EXCHANGE_NAME = "boot.exchange.topic";
    public static final String QUEUE_NAME1 = "boot.queue.order1";
    public static final String QUEUE_NAME2 = "boot.queue.order2";
    public static final String QUEUE_NAME3 = "boot.queue.order3";

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = QUEUE_NAME1),
                    exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = "order.*"
            )
    })
    public void getMessage1(String dateString, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取当前消息的 deliveryTag
            channel.basicAck(deliveryTag, false);
            log.info("收到了信息...\nexchange = {} \nqueue = {} \nkey = {}  \ndateString = {} \nmessage = {} \nchannel = {}", EXCHANGE_NAME, QUEUE_NAME1, "order.*", dateString, message, channel);
        } catch (Exception e) {
            log.error("getMessage1 异常 :{}", e.getMessage(), e);
            // 获取当前消息是否是重复投递的
            //      redelivered 为 true：说明当前消息已经重复投递过一次了
            //      redelivered 为 false：说明当前消息是第一次投递
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            // 核心操作失败：返回 NACK 信息
            // requeue 参数：控制消息是否重新放回队列
            //      取值为 true：重新放回队列，broker 会重新投递这个消息
            //      取值为 false：不重新放回队列，broker 会丢弃这个消息
            if (redelivered) {
                // 如果当前消息已经是重复投递的，说明此前已经重试过一次啦，所以 requeue 设置为 false，表示不重新放回队列
                channel.basicNack(deliveryTag, false, false);
            } else {
                // 如果当前消息是第一次投递，说明当前代码是第一次抛异常，尚未重试，所以 requeue 设置为 true，表示重新放回队列在投递一次
                channel.basicNack(deliveryTag, false, true);
            }
            // reject 表示拒绝
            // 辨析：basicNack() 和 basicReject() 方法区别
            // basicNack()能控制是否批量操作
            // basicReject()不能控制是否批量操作
            // channel.basicReject(deliveryTag, true);
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = QUEUE_NAME2),
                    exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = "order.success"
            )
    })
    public void getMessage2(String dateString, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("收到了信息...\nexchange = {} \nqueue = {} \nkey = {}  \ndateString = {} \nmessage = {} \nchannel = {}", EXCHANGE_NAME, QUEUE_NAME2, "order.success", dateString, message, channel);
        } catch (Exception e) {
            log.error("getMessage2 异常 :{}", e.getMessage(), e);
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = QUEUE_NAME3),
                    exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
                    key = "*.*"
            )
    })
    public void getMessage3(String dateString, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("收到了信息...\nexchange = {} \nqueue = {} \nkey = {}  \ndateString = {} \nmessage = {} \nchannel = {}", EXCHANGE_NAME, QUEUE_NAME3, "*.*", dateString, message, channel);
        } catch (Exception e) {
            log.error("getMessage3 异常 :{}", e.getMessage(), e);
        }
    }
}
