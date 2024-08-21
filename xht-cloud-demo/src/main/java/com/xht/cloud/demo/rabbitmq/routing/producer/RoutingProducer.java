package com.xht.cloud.demo.rabbitmq.routing.producer;

import cn.hutool.core.thread.ThreadUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xht.cloud.demo.rabbitmq.RabbitMQConnectionUtils;

import java.util.Scanner;

/**
 * 描述 ：消息发送端（生产者）
 *
 * @author : 小糊涂
 **/
public class RoutingProducer {
    static final String QUEUE_NAME1 = "routing_queue1";
    static final String QUEUE_NAME2 = "routing_queue2";
    static final String EXCHANGE_NAME = "routing_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true, false, false, null);
        channel.queueDeclare(QUEUE_NAME1, true, false, false, null);
        channel.queueDeclare(QUEUE_NAME2, true, false, false, null);
        // 5、绑定队列和交换机
        // 参数1. queue：队列名称
        // 参数2. exchange：交换机名称
        // 参数3. routingKey：路由键，绑定规则
        //     如果交换机的类型为fanout，routingKey设置为""
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "#.error");
        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "order.*");
        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "*.*");
        System.out.print("请输入你的信息：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 发送消息
            channel.basicPublish(EXCHANGE_NAME, "order.info", null, String.format("order.info:%s",message).getBytes());
            channel.basicPublish(EXCHANGE_NAME, "order.error", null, String.format("order.error:%s",message).getBytes());
            channel.basicPublish(EXCHANGE_NAME, "goods.info", null, String.format("goods.info:%s",message).getBytes());
            channel.basicPublish(EXCHANGE_NAME, "goods.error", null, String.format("goods.error:%s",message).getBytes());
            System.err.println("消息发送成功");
            ThreadUtil.sleep(500);
            System.out.print("请输入你的信息:");
        }
        RabbitMQConnectionUtils.close(channel, connection);
    }
}
