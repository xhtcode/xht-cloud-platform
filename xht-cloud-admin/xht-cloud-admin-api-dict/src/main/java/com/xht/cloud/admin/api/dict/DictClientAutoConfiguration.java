package com.xht.cloud.admin.api.dict;

import com.xht.cloud.admin.api.dict.feign.SysDictClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static com.xht.cloud.framework.core.server.ServerConstants.XHT_CLOUD_ADMIN;

/**
 * 描述 ：系统管理模块 字典模板 自动注入
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(clients = SysDictClient.class)
public class DictClientAutoConfiguration {

    public DictClientAutoConfiguration() {
        log.debug("[系统管理模块:{}]  字典 外部模块调用 注入", XHT_CLOUD_ADMIN);
    }
}
