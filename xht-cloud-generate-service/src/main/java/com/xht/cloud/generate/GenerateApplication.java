package com.xht.cloud.generate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述 ：代码生成器
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class GenerateApplication {
    public static void main(String[] args) {
        SpringApplication.run(GenerateApplication.class, args);
    }
}
