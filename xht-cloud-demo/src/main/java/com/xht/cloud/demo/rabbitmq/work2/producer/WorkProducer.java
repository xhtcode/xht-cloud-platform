package com.xht.cloud.demo.rabbitmq.work2.producer;

import cn.hutool.core.thread.ThreadUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xht.cloud.demo.rabbitmq.RabbitMQConnectionUtils;

import java.util.Scanner;

/**
 * 描述 ：消息发送端（生产者）
 *
 * @author : 小糊涂
 **/
public class WorkProducer {
    static final String QUEUE_NAME = "work_queue2";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.print("请输入你的信息：");
        Scanner scanner = new Scanner(System.in);
        //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者(同一时刻服务器只会发送一条消息给消费者),消费者端发送了ack后才会接收下一个消息。
        channel.basicQos(1);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.print("请输入你的信息:");
        }
        RabbitMQConnectionUtils.close(channel, connection);
    }
}
