package com.xht.cloud.framework.mongodb.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：mongodb 配置类
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.mongodb")
public class MongodbProperties {
}
