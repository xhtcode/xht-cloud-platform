package com.xht.cloud.auth;

import com.xht.cloud.framework.security.authorization.EnableAuthorizationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 描述 ：授权服务启动
 *
 * @author 小糊涂
 **/
@EnableDiscoveryClient
@EnableAuthorizationServer
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}