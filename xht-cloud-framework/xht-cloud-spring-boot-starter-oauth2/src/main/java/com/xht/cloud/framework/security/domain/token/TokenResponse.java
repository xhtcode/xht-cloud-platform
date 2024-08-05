package com.xht.cloud.framework.security.domain.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.cloud.framework.core.domain.response.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "token返回值")
public class TokenResponse extends AbstractResponse implements Serializable {

    /**
     * 访问令牌
     */
    @Schema(name = "access_token", defaultValue = "访问令牌")
    @JsonProperty(value = "access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @Schema(name = "refresh_token", defaultValue = "刷新令牌")
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    /**
     * 授权范围
     */
    @Schema(name = "scope", defaultValue = "授权范围")
    @JsonProperty(value = "scope")
    private String scope;

    /**
     * oidc 令牌
     */
    @Schema(name = "id_token", defaultValue = "oidc 令牌")
    @JsonProperty(value = "id_token")
    private String idToken;

    /**
     * 访问令牌类型
     */
    @Schema(name = "token_type", defaultValue = "访问令牌类型")
    @JsonProperty(value = "token_type")
    private String tokenType;

    /**
     * 令牌过期时间
     */
    @Schema(name = "expires_in", defaultValue = "令牌过期时间")
    @JsonProperty(value = "expires_in")
    private Long expiresIn;

    /**
     * 额外的参数
     */
    @Schema(name = "additional", defaultValue = "额外的参数")
    @JsonProperty(value = "additional")
    private Map<String, Object> additionalParameters;

}
