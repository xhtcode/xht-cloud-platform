package com.xht.cloud.framework.security.domain.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;

/**
 * 描述 ：Redis认证
 * <a href="https://docs.spring.io/spring-data/redis/reference/repositories/core-concepts.html">参考</a>
 *
 * @author 小糊涂
 **/
@Data
@RedisHash("oauth2:authorization")
public class RedisOAuth2Authorization implements Serializable {

    @Id
    private String id;

    /**
     * 客户端ID.
     */
    private String registeredClientId;

    /**
     * 认证用户名.
     */
    private String principalName;

    /**
     * 认证类型.
     */
    private String authorizationGrantType;

    /**
     * 认证范围.
     */
    private String authorizedScopes;

    /**
     * 认证信息.
     */
    private String attributes;

    /**
     * 认证状态.
     */
    @Indexed
    private String state;

    /**
     * 授权码.
     */
    @Indexed
    private String authorizationCodeValue;

    /**
     * 授权码签发时间.
     */
    private Instant authorizationCodeIssuedAt;

    /**
     * 授权码过期时间.
     */
    private Instant authorizationCodeExpiresAt;

    /**
     * 授权码元数据.
     */
    private String authorizationCodeMetadata;

    /**
     * 令牌.
     */
    @Indexed
    private String accessTokenValue;

    /**
     * 令牌签发时间.
     */
    private Instant accessTokenIssuedAt;

    /**
     * 令牌过期时间.
     */
    private Instant accessTokenExpiresAt;

    /**
     * 令牌元数据.
     */
    private String accessTokenMetadata;

    /**
     * 令牌类型.
     */
    private String accessTokenType;

    /**
     * 令牌范围.
     */
    private String accessTokenScopes;

    /**
     * OID.
     */
    @Indexed
    private String oidcIdTokenValue;

    /**
     * OID签发时间.
     */
    private Instant oidcIdTokenIssuedAt;

    /**
     * OID过期时间.
     */
    private Instant oidcIdTokenExpiresAt;

    /**
     * OID元数据.
     */
    private String oidcIdTokenMetadata;

    /**
     * OID属性.
     */
    private String oidcIdTokenClaims;

    /**
     * 刷新令牌.
     */
    @Indexed
    private String refreshTokenValue;

    /**
     * 刷新令牌签发时间.
     */
    private Instant refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间.
     */
    private Instant refreshTokenExpiresAt;

    /**
     * 刷新令牌元数据.
     */
    private String refreshTokenMetadata;

    /**
     * 用户码.
     */
    @Indexed
    private String userCodeValue;

    /**
     * 用户码签发时间.
     */
    private Instant userCodeIssuedAt;

    /**
     * 用户码过期时间.
     */
    private Instant userCodeExpiresAt;

    /**
     * 用户码元数据.
     */
    private String userCodeMetadata;

    /**
     * 设备码.
     */
    @Indexed
    private String deviceCodeValue;

    /**
     * 设备码签发时间.
     */
    private Instant deviceCodeIssuedAt;

    /**
     * 设备码过期时间.
     */
    private Instant deviceCodeExpiresAt;

    /**
     * 设备码元数据.
     */
    private String deviceCodeMetadata;

    /**
     * 过期时间（秒）.
     */
    @TimeToLive
    private Long ttl;

}
