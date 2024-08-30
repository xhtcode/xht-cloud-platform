package com.xht.cloud.admin.api.user;

import com.xht.cloud.admin.api.user.feign.UserCenterClient;
import com.xht.cloud.framework.core.server.ServerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 描述 ：系统管理模块 用户外部调用模板 自动注入
 * 当前微服务不是`xht-cloud-admin`则注入
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(clients = {UserCenterClient.class})
public class UserClientAutoConfiguration {

    public UserClientAutoConfiguration() {
        log.info("[系统管理模块:{}]   用户 外部模块调用 注入", ServerConstants.XHT_CLOUD_ADMIN);
    }
}
