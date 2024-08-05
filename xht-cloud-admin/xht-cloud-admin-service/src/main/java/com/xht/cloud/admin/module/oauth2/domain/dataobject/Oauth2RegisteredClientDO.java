package com.xht.cloud.admin.module.oauth2.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "oauth2_registered_client")
public class Oauth2RegisteredClientDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 客户端标识符
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 客户端签发日期
     */
    @TableField(value = "client_id_issued_at")
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端密钥
     */
    @TableField(value = "client_secret")
    private String clientSecret;

    /**
     * 客户端过期时间
     */
    @TableField(value = "client_secret_expires_at")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 是否自动放行
     */
    @TableField(value = "auto_approve")
    private String autoApprove;

    /**
     * 客户端名称
     */
    @TableField(value = "client_name")
    private String clientName;

    /**
     * 客户端可以使用的身份验证方法。支持的值为private_key_jwt,,and(publicclients).client_secret_basicclient_secret_postclient_secret_jwtnone
     */
    @TableField(value = "client_authentication_methods")
    private String clientAuthenticationMethods;

    /**
     * 客户端可以使用的授权类型
     */
    @TableField(value = "authorization_grant_types")
    private String authorizationGrantTypes;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect_uris")
    private String redirectUris;

    /**
     * 客户端可用于注销的注销后重定向URI。
     */
    @TableField(value = "post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    /**
     * 允许客户端请求的范围
     */
    @TableField(value = "scopes")
    private String scopes;

    /**
     * token有效期
     */
    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity;

}
