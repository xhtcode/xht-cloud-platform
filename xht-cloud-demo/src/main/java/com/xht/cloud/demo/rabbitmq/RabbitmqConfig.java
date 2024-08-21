package com.xht.cloud.demo.rabbitmq;

import lombok.Data;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Data
public final class RabbitmqConfig {
    /**
     * 主机地址
     */private String host = "127.0.0.1";

    /**
     * 连接端口号
     */private Integer port = 5672;

    /**
     * 虚拟主机名称
     */private String virtualHost = "/";

    /**
     * 连接用户名
     */private String username = "guest";

    /**
     * 连接密码
     */private String password = "123456";
}
