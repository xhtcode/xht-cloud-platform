package com.xht.cloud.framework.mybatis;

import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import com.xht.cloud.framework.starter.signature.ApiSignatureProperties;
import com.xht.cloud.framework.mybatis.filter.ApiSignatureDataBaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 描述 ： 签名认证 配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnClass(value = JdbcTemplate.class)
@EnableConfigurationProperties(ApiSignatureProperties.class)
@ConditionalOnProperty(value = "xht.cloud.web.signature.enabled", havingValue = "true", matchIfMissing = true)
public class ApiSignatureDatabaseAutoConfig {

    @Bean
    @ConditionalOnBean(JdbcTemplate.class)
    @ConditionalOnProperty(value = "xht.cloud.web.signature.type", havingValue = "database", matchIfMissing = true)
    public ApiSignatureFilter apiSignatureDataBaseFilter(ApiSignatureProperties apiSignatureProperties,
                                                         JdbcTemplate jdbcTemplate) {
        return new ApiSignatureDataBaseFilter(apiSignatureProperties, jdbcTemplate);
    }
}
