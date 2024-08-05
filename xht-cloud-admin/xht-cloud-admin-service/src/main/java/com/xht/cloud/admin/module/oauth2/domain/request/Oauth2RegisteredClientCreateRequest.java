package com.xht.cloud.admin.module.oauth2.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.IntegerInterval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述 ：oauth2 客户端信息-增加请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "Oauth2RegisteredClientRequest(oauth2 客户端信息-增加请求信息)")
public class Oauth2RegisteredClientCreateRequest extends CreateRequest {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    @NotEmpty(message = "客户端ID不合法！")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    @NotEmpty(message = "客户端密钥不合法！")
    private String clientSecret;

    /**
     * 客户端过期时间
     */
    @Schema(description = "客户端过期时间")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 授权模式
     */
    @Schema(description = "授权模式")
    @NotEmpty(message = "授权模式不合法！")
    private Set<String> authorizationGrantTypes;


    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    @NotEmpty(message = "是否自动放行不合法！")
    private String autoApprove;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    @NotEmpty(message = "客户端名称不合法！")
    private String clientName;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    @NotEmpty(message = "重定向地址不合法！")
    private String redirectUris;

    /**
     * 允许客户端请求的范围
     */
    @Schema(description = "允许客户端请求的范围")
    @NotEmpty(message = "允许客户端请求的范围不合法！")
    private Set<String> scopes;

    /**
     * token有效期
     */
    @Schema(description = "token有效期")
    @IntegerInterval(min = 5 * 60 * 100, max = 12 * 30 * 60 * 100)
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @Schema(description = "刷新令牌有效期")
    @IntegerInterval(min = 5 * 60 * 100, max = 12 * 30 * 60 * 100)
    private Integer refreshTokenValidity;

}
