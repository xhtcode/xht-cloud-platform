package com.xht.cloud.framework.starter.configuration;

import com.xht.cloud.framework.core.constant.SpringPropertiesNameConstant;
import com.xht.cloud.framework.starter.boot.BannerApplicationRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;

/**
 * 描述 ：小糊涂spring boot 自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
public class XhtSpringBootAutoConfiguration {
    /**
     * Banner 的自动配置类
     *
     * @param applicationName 服务应用名称
     * @return {@link BannerApplicationRunner}
     */
    @Bean
    public BannerApplicationRunner bannerApplicationRunner(@Value(value = SpringPropertiesNameConstant.SPRING_APPLICATION_KEY_SPEL) String applicationName,
                                                           ServerProperties serverProperties
    ) {
        return new BannerApplicationRunner(applicationName, serverProperties);
    }

}
