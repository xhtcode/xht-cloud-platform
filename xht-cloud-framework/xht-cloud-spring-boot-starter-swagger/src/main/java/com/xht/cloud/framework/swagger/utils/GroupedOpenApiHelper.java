package com.xht.cloud.framework.swagger.utils;

import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.http.HttpHeaders;

/**
 * 描述 ：spring-doc快速构建辅助类
 *
 * @author 小糊涂
 **/
public final class GroupedOpenApiHelper {

    public static GroupedOpenApi build(String group, String... path) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch(path)
                .addOperationCustomizer((operation, handlerMethod) -> operation.addParametersItem(buildSecurityHeaderParameter()))
                .build();
    }


    /**
     * 构建 Authorization 认证请求头参数
     * <p>
     * 解决 Knife4j <a href="https://gitee.com/xiaoym/knife4j/issues/I69QBU">Authorize 未生效，请求header里未包含参数</a>
     *
     * @return 认证参数
     */
    private static Parameter buildSecurityHeaderParameter() {
        return new Parameter()
                .name(HttpHeaders.AUTHORIZATION) // header 名
                .description("认证 Token") // 描述
                .in(String.valueOf(SecurityScheme.In.HEADER)) // 请求 header
                .schema(new StringSchema()
                        ._default("Bearer access_token")
                        .name("请求 header")
                        .description("认证 Token"));
    }
}
