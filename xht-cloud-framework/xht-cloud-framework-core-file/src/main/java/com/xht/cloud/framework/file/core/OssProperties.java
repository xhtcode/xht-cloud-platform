package com.xht.cloud.framework.file.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：minio配置类
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.oss")
public class OssProperties {

    /**
     * 存储桶
     */
    private String bucketName;

    /**
     * host {@code http://127.0.0.1:8001}
     *
     */
    private String host;

    /**
     * 公钥
     */
    private String accessKey;

    /**
     * 私钥
     */
    private String secretKey;

    /**
     * 分块配置
     */
    private PartProperties part = new PartProperties();

}
