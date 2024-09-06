package com.xht.cloud.admin.api.dict;

import com.xht.cloud.admin.api.dict.feign.SysDictClient;
import com.xht.cloud.admin.api.dict.translation.TransDictCodeStrategy;
import com.xht.cloud.admin.api.dict.translation.TransDictIdStrategy;
import com.xht.cloud.framework.jackson.translation.strategy.AbstractTransDictStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

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
        log.debug("[系统管理模块]  字典 外部模块调用 注入");
    }

    /**
     * 字典id处理
     *
     * @return 字典翻译
     */
    @Bean
    public AbstractTransDictStrategy transDictIdStrategy() {
        return new TransDictIdStrategy();
    }

    /**
     * 字典编码处理
     *
     * @return 字典翻译
     */
    @Bean
    public AbstractTransDictStrategy transDictCodeStrategy() {
        return new TransDictCodeStrategy();
    }
}
