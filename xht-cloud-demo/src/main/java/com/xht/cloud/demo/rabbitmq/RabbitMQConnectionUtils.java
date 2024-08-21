package com.xht.cloud.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Objects;

/**
 * 描述 ：RabbitMQ工具类
 *
 * @author : 小糊涂
 **/
public final class RabbitMQConnectionUtils {private final static RabbitmqConfig rabbitmqConfig = new RabbitmqConfig();

    /**
     * 获取 rabbitmq 连接对象
     *
     * @return {@link Connection} 连接
     */
    public static Connection getConnection() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();  // 创建连接工厂
        connectionFactory.setHost(rabbitmqConfig.getHost());    // 设置主机地址
        connectionFactory.setPort(rabbitmqConfig.getPort());   // 设置连接端口号：默认为 5672
        connectionFactory.setVirtualHost(rabbitmqConfig.getVirtualHost());    // 虚拟主机名称：默认为 /
        connectionFactory.setUsername(rabbitmqConfig.getUsername());// 设置连接用户名；默认为guest
        connectionFactory.setPassword(rabbitmqConfig.getPassword()); // 设置连接密码；默认为guest
        return connectionFactory.newConnection();   // 创建连接
    }

    /**
     * 关闭
     *
     * @param channel    {@link Channel}通道
     * @param connection {@link Connection}连接
     */
    public static void close(Channel channel, Connection connection) {
        if (Objects.nonNull(channel)) {
            try {
                channel.close();
            } catch (Exception ignored) {
            }
        }
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }
    }
}
