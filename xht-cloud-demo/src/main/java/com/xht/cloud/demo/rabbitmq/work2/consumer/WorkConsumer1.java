package com.xht.cloud.demo.rabbitmq.work2.consumer;

import cn.hutool.core.thread.ThreadUtil;
import com.rabbitmq.client.*;
import com.xht.cloud.demo.rabbitmq.RabbitMQConnectionUtils;

import java.io.IOException;

/**
 * 描述 ：消息接收端（消费者）
 *
 * @author : 小糊涂
 **/
public class WorkConsumer1 {
    static final String QUEUE_NAME = "work_queue2";

    public static void main(String[] args) throws Exception {
        // 创建连接 Connection
        Connection connection = RabbitMQConnectionUtils.getConnection();
        // 创建Channel
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        // 创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 接收消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("WorkConsumer1：" + new String(body));
                ThreadUtil.sleep(1000);
                //消费完一条消息需要自动发送确认消息给MQ
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
