package com.xht.cloud.admin.api.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 描述 ：系统管理模块 序列外部调用模板 自动注入
 * 当前微服务不是`xht-cloud-admin`则注入
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(basePackages = "com.xht.cloud.admin.api.sequence")
public class SequenceClientAutoConfiguration {

    public SequenceClientAutoConfiguration() {
    }
}
