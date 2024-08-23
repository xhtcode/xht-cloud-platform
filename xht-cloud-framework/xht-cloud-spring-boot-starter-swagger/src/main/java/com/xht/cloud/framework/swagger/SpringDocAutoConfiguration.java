package com.xht.cloud.framework.swagger;

import com.xht.cloud.framework.swagger.core.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * 描述 ：spring doc 配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SpringDocAutoConfiguration {
    private final SwaggerProperties swaggerProperties;

    public SpringDocAutoConfiguration(SwaggerProperties swaggerProperties) {
        log.debug(">>>>>>swagger-start 自动配置类<<<<<<");
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI springShopOpenAPI() {
        // @formatter:off
        Map<String, SecurityScheme> securitySchemas = buildSecuritySchemes();
        OpenAPI openAPI = new OpenAPI()
                .info(
                        new Info()
                                .title(swaggerProperties.getTitle())
                                .description(swaggerProperties.getDescription())
                                .version(swaggerProperties.getVersion())
                                .contact(new Contact()
                                        .name(swaggerProperties.getAuthor())
                                        .url(swaggerProperties.getUrl())
                                        .email(swaggerProperties.getEmail()))
                )
                .components(new Components().securitySchemes(securitySchemas))
                .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION));
        securitySchemas.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));
        // @formatter:on
        return openAPI;
    }


    /**
     * 安全模式，这里配置通过请求头 Authorization 传递 token 参数
     */
    private Map<String, SecurityScheme> buildSecuritySchemes() {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY) // 类型
                .name(AUTHORIZATION) // 请求头的 name
                .in(SecurityScheme.In.HEADER); // token 所在位置
        securitySchemes.put(AUTHORIZATION, securityScheme);
        return securitySchemes;
    }

}
