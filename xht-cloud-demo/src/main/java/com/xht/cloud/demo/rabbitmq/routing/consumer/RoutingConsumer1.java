package com.xht.cloud.demo.rabbitmq.routing.consumer;

import com.rabbitmq.client.*;
import com.xht.cloud.demo.rabbitmq.RabbitMQConnectionUtils;

/**
 * 描述 ：消息接收端（消费者）
 *
 * @author : 小糊涂
 **/
public class RoutingConsumer1 {
    static final String QUEUE_NAME1 = "routing_queue1";
    public static void main(String[] args) throws Exception {
        // 创建连接 Connection
        Connection connection = RabbitMQConnectionUtils.getConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        // 创建队列
        channel.queueDeclare(QUEUE_NAME1, true, false, false, null);
        // 接收消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println("WorkConsumer1：" + new String(body));
            }
        };
        channel.basicConsume(QUEUE_NAME1, true, consumer);
    }
}
