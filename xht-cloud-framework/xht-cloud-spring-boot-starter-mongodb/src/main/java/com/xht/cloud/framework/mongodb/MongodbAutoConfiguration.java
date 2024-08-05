package com.xht.cloud.framework.mongodb;

import com.mongodb.client.gridfs.GridFSBucket;
import com.xht.cloud.framework.mongodb.core.MongodbProperties;
import com.xht.cloud.framework.mongodb.utils.GridFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 描述 ：mongodb 自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(MongodbProperties.class)
public class MongodbAutoConfiguration {

    @Bean
    @ConditionalOnBean(GridFSBucket.class)
    public GridFSUtils gridFSUtils(GridFSBucket gridFSBucket) {
        return new GridFSUtils(gridFSBucket);
    }
}
