package com.xht.cloud.demo.rabbitmq.work.producer;

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
    static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.print("请输入你的信息：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.err.println("消息发送成功");
            ThreadUtil.sleep(500);
            System.out.print("请输入你的信息:");
        }
        RabbitMQConnectionUtils.close(channel, connection);
    }
}
