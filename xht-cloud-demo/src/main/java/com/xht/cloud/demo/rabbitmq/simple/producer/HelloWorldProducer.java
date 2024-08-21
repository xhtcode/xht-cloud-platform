package com.xht.cloud.demo.rabbitmq.simple.producer;

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
public class HelloWorldProducer {
    public static void main(String[] args) throws Exception {

        Connection connection = RabbitMQConnectionUtils.getConnection();
        Channel channel = connection.createChannel();     // 创建频道
        // 声明（创建）队列
        // queue      参数1：队列名称
        // durable    参数2：是否定义持久化队列，当 MQ 重启之后还在
        // exclusive  参数3：是否独占本次连接。若独占，只能有一个消费者监听这个队列且 Connection 关闭时删除这个队列
        // autoDelete 参数4：是否在不使用的时候自动删除队列，也就是在没有Consumer时自动删除
        // arguments  参数5：队列其它参数
        channel.queueDeclare("simple_queue", true, false, false, null);
        System.out.print("请输入你的信息：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 参数1：交换机名称,如果没有指定则使用默认Default Exchange
            // 参数2：路由key,简单模式可以传递队列名称
            // 参数3：配置信息
            // 参数4：消息内容
            channel.basicPublish("", "simple_queue", null, message.getBytes());
            System.err.println("消息发送成功");
            ThreadUtil.sleep(500);
            System.out.print("请输入你的信息:");
        }
        RabbitMQConnectionUtils.close(channel, connection);
    }
}
