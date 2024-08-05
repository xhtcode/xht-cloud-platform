package com.xht.cloud.framework.starter.properties;

import com.xht.cloud.framework.core.config.CommonConfigProperties;
import lombok.Data;

/**
 * 描述 ：自定义jdbc连接配置扩展类
 *
 * @author 小糊涂
 **/
@Data
public class JdbcProperties extends CommonConfigProperties {

    /**
     * 连接地址
     */
    private String jdbcUrl;

    /**
     * 驱动
     */
    private String driverClassName;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
