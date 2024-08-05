package com.xht.cloud.framework.starter.signature;

import com.xht.cloud.framework.core.config.CommonConfigProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ：接口签名 配置类型
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.web.signature")
public class ApiSignatureProperties extends CommonConfigProperties {

    /**
     * 是否读取配置文件 true 读取
     */
    private ApiSignatureType type = ApiSignatureType.PROPERTIES;

    /**
     * 应用的唯一标识
     */
    private String appId;

    /**
     * 公匙（相当于账号）
     */
    private String appKey;

    /**
     * 超时时间默认60秒
     */
    private long timeOut = 60 * 1000L;


    /**
     * 服务名称 当配置 type为RPC 模式时必填
     */
    private String serverName;


}
