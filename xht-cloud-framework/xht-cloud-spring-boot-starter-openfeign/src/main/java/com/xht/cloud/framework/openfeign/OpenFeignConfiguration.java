package com.xht.cloud.framework.openfeign;

import com.xht.cloud.framework.openfeign.interceptor.FeignRequestInterceptor;
import com.xht.cloud.framework.openfeign.core.OpenFeignProperties;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 描述 ：openFeign自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(value = {OpenFeignProperties.class})
public class OpenFeignConfiguration {

    @Bean
    public Logger.Level logger() {
        return Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor(OpenFeignProperties openFeignProperties) {
        return new FeignRequestInterceptor(openFeignProperties);
    }

    /**
     * 最大请求次数为3，初始间隔时间为100ms<br/>
     * 下次间隔时间1.5倍递增，重试间最大间隔时间为1s
     *
     * @return {@link Retryer}
     */

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

}
