package com.xht.cloud.admin.module.oauth2.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "Oauth2RegisteredClientDTO(oauth2 客户端信息-响应信息)")
public class Oauth2RegisteredClientResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端过期时间
     */
    @Schema(description = "客户端过期时间")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private String autoApprove;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    private String clientName;

    /**
     * 授权模式
     */
    @Schema(description = "授权模式")
    private Set<String> authorizationGrantTypes;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirectUris;

    /**
     * 允许客户端请求的范围
     */
    @Schema(description = "允许客户端请求的范围")
    private Set<String> scopes;

    /**
     * token有效期
     */
    @Schema(description = "token有效期")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @Schema(description = "刷新令牌有效期")
    private Integer refreshTokenValidity;

}
