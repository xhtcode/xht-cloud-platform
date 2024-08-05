package com.xht.cloud.framework.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class OAuth2AuthenticationProviderUtils {
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(ID_TOKEN);


    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    public static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

    /**
     * 生成 Access token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @param tokenGenerator       token 生成策略{@link OAuth2TokenGenerator}
     * @return {@link OAuth2AccessToken}
     */
    public static OAuth2AccessToken createOAuth2AccessToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.id(accessToken.getTokenValue())
                    .token(accessToken,
                            (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                                    ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.id(accessToken.getTokenValue()).accessToken(accessToken);
        }
        return accessToken;
    }

    /**
     * 生成 Refresh token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @param tokenGenerator       token 生成策略{@link OAuth2TokenGenerator}
     * @param clientPrincipal {@link OAuth2ClientAuthenticationToken}
     * @param registeredClient {@link RegisteredClient}
     * @return {@link OAuth2RefreshToken}
     */
    public static OAuth2RefreshToken creatOAuth2RefreshToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                             OAuth2ClientAuthenticationToken clientPrincipal,
                                                             RegisteredClient registeredClient) {
        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }
        return refreshToken;
    }

    /**
     * 创建oidc Token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @param tokenGenerator       token 生成策略{@link OAuth2TokenGenerator}
     * @return {@link OidcIdToken}
     */
    public static OidcIdToken createOidcIdToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder,
                                                OAuth2Authorization.Builder authorizationBuilder,
                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        DefaultOAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(ID_TOKEN_TOKEN_TYPE)
                // ID令牌定制器可能需要访问访问令牌、刷新令牌
                .authorization(authorizationBuilder.build())
                .build();
        OAuth2Token generatedIdToken = Optional.ofNullable(tokenGenerator.generate(tokenContext))
                .orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error("code", "desc", "uri")));
        // 生成id_token
        OidcIdToken idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
        authorizationBuilder.token(idToken, (metadata) -> metadata
                .put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        return idToken;
    }

    /**
     * 添加oidc的参数，如果没有就返回一个空的map
     *
     * @param idToken {@link OidcIdToken}
     * @return {@link Map <String, Object>}
     */
    public static Map<String, Object> idTokenAdditionalParameters(OidcIdToken idToken) {
        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(ID_TOKEN, idToken.getTokenValue());
        }
        return additionalParameters;
    }
}
