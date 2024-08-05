package com.xht.cloud.framework.core.constant;

/**
 * 描述 ：常用的spring 配置key名
 *
 * @author 小糊涂
 **/
public interface SpringPropertiesNameConstant {

    /**
     * 服务名称 KEY值
     */
    String SPRING_APPLICATION_KEY = "spring.application.name";
    String SPRING_APPLICATION_KEY_SPEL = "${spring.application.name:xht-cloud}";


    /**
     * 端口号 KEY值
     */
    String SERVER_PORT_KEY = "server.port";
    String SERVER_PORT_KEY_SPEL = "${server.port:8080}";
}
