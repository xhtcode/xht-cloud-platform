package com.xht.cloud.framework.security.core;


import com.xht.cloud.framework.core.config.CommonConfigProperties;
import com.xht.cloud.framework.core.developer.DeveloperTool;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：自定义 安全配置
 *
 * @author 小糊涂
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.security")
public class SecurityConfigProperties {

    /**
     * 自定义 密码模式
     */
    private CommonConfigProperties password = new CommonConfigProperties(true);

    /**
     * swagger 安全配置
     */
    private CommonConfigProperties swagger = new CommonConfigProperties(true);

    /**
     * 白名单
     */
    private List<String> whiteUrls = new ArrayList<>();

    /**
     * 安全请求头值
     */
    private String securityHeaderValue;

    public List<String> formatWhiteUrls() {
        boolean activeProfileProd = DeveloperTool.isProdActiveProfile(SpringContextUtil.getActiveProfile());
        List<String> whiteUrls = this.getWhiteUrls();
        whiteUrls.add("/error");
        whiteUrls.add("/health");
        whiteUrls.add("/favicon.ico");
        if (Objects.equals(this.getSwagger().isEnabled(), Boolean.TRUE) && !activeProfileProd) {
            whiteUrls.add("/swagger-ui/**");
            whiteUrls.add("/doc.html");
            whiteUrls.add("/webjars/**");
            whiteUrls.add(String.format("%s/**", SpringContextUtil.getProperty("springdoc.api-docs.path", "/v3/api-docs")));
        }
        return whiteUrls;
    }

}
