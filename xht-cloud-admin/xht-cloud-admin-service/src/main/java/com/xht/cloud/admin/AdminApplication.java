package com.xht.cloud.admin;

import com.xht.cloud.framework.security.resource.EnableResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 描述 ：系统管理业务模块 启动器
 *
 * @author 小糊涂
 **/
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
