package com.xht.cloud.framework.core.server;

/**
 * 描述 ：服务 常量
 *
 * @author 小糊涂
 **/
public final class ServerConstants {

    public static final String XHT_CLOUD_ADMIN = "xht-cloud-admin-service";
    public static final String XHT_CLOUD_ADMIN_NE_SPEL = "#{T(com.xht.cloud.framework.utils.spring.ServerUtils).notThisServer(T(com.xht.cloud.framework.core.server.ServerConstants).XHT_CLOUD_ADMIN)}";

}
