package com.xht.cloud.framework.security.oauth2.server.authorization;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.security.domain.redis.RedisOAuth2Authorization;
import com.xht.cloud.framework.security.repository.RedisOAuth2AuthorizationRepository;
import com.xht.cloud.framework.utils.jackson.CustomJacksonModule;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;

/**
 * 描述 ： OAuth2 授权信息
 *
 * @author 小糊涂
 **/
@Slf4j
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RegisteredClientRepository registeredClientRepository;

    private final RedisOAuth2AuthorizationRepository redisOAuth2AuthorizationRepository;

    // @formatter:off
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModules(SecurityJackson2Modules.getModules(RedisOAuth2AuthorizationService.class.getClassLoader()))
            .registerModule(new OAuth2AuthorizationServerJackson2Module())
            .registerModule(new CustomJacksonModule());
    // @formatter:on

    public RedisOAuth2AuthorizationService(RegisteredClientRepository registeredClientRepository, RedisOAuth2AuthorizationRepository redisOAuth2AuthorizationRepository) {
        Assert.isTrue(ObjectUtil.isNull(registeredClientRepository), "查询不到客户端存储器");
        Assert.isTrue(ObjectUtil.isNull(redisOAuth2AuthorizationRepository), "查询不到授权存储器");
        this.registeredClientRepository = registeredClientRepository;
        this.redisOAuth2AuthorizationRepository = redisOAuth2AuthorizationRepository;
    }

    /**
     * 保存授权信息 {@link OAuth2Authorization}.
     *
     * @param authorization the {@link OAuth2Authorization}
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        // @formatter:off
        Assert.notNull(authorization, "授权信息不能为空!");
        log.debug("保存授权信息{}", authorization);
        RedisOAuth2Authorization redisOAuth2Authorization = convert(authorization);
        assert redisOAuth2Authorization != null;
        List<Instant> expireAtList = Stream.of(
                redisOAuth2Authorization.getAuthorizationCodeExpiresAt(),
                redisOAuth2Authorization.getAccessTokenExpiresAt(),
                redisOAuth2Authorization.getOidcIdTokenExpiresAt(),
                redisOAuth2Authorization.getRefreshTokenExpiresAt(),
                redisOAuth2Authorization.getUserCodeExpiresAt(),
                redisOAuth2Authorization.getDeviceCodeExpiresAt()
                )
                .filter(ObjectUtil::isNotNull).toList();
        expireAtList.stream()
                .max(Comparator.comparing(Instant::getEpochSecond))
                .ifPresent(instant -> redisOAuth2Authorization.setTtl(ChronoUnit.SECONDS.between(Instant.now(), instant)));
        // 先删除后新增
        redisOAuth2AuthorizationRepository.deleteById(authorization.getId());
        redisOAuth2AuthorizationRepository.save(redisOAuth2Authorization);
        log.debug("保存授权信息成功{}", authorization.getPrincipalName());
        // @formatter:on
    }

    /**
     * 删除授权信息 {@link OAuth2Authorization}.
     *
     * @param authorization the {@link OAuth2Authorization}
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "授权信息不能为空!");
        redisOAuth2AuthorizationRepository.deleteById(authorization.getId());
        log.debug("删除授权信息成功{}", authorization.getId());
    }

    /**
     * 根据 id 查询授权信息
     *
     * @param id the authorization identifier
     * @return the {@link OAuth2Authorization} if found, otherwise {@code null}
     */
    @Override
    public OAuth2Authorization findById(String id) {
        // @formatter:off
        return redisOAuth2AuthorizationRepository
                .findById(id)
                .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                .orElse(null);
        // @formatter:on
    }

    /**
     * 根据token和token类型查询授权信息
     *
     * @param token     token令牌
     * @param tokenType token令牌类型 {@link OAuth2TokenType token type}
     * @return the {@link OAuth2Authorization} if found, otherwise {@code null}
     */
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        // @formatter:off
        Assert.notNull(token, "授权信息`token`不能为空!");
        log.debug("根据token={}和tokenType={}查询授权信息", token, tokenType);
        return switch (tokenType.getValue()) {
            case STATE -> redisOAuth2AuthorizationRepository
                    .findByState(token).map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                    .orElse(null);
            case CODE ->
                    redisOAuth2AuthorizationRepository
                            .findByAuthorizationCodeValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case ACCESS_TOKEN ->
                    redisOAuth2AuthorizationRepository
                            .findByAccessTokenValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case ID_TOKEN ->
                    redisOAuth2AuthorizationRepository
                            .findByOidcIdTokenValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case REFRESH_TOKEN ->
                    redisOAuth2AuthorizationRepository
                            .findByRefreshTokenValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case USER_CODE ->
                    redisOAuth2AuthorizationRepository
                            .findByUserCodeValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case DEVICE_CODE ->
                    redisOAuth2AuthorizationRepository
                            .findByDeviceCodeValue(token)
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId())))
                            .orElse(null);
            case "full" ->
                    redisOAuth2AuthorizationRepository.findByState(token)
                            .or(() -> redisOAuth2AuthorizationRepository.findByAuthorizationCodeValue(token))
                            .or(() -> redisOAuth2AuthorizationRepository.findByAccessTokenValue(token))
                            .or(() -> redisOAuth2AuthorizationRepository.findByOidcIdTokenValue(token))
                            .or(() -> redisOAuth2AuthorizationRepository.findByRefreshTokenValue(token))
                            .or(() -> redisOAuth2AuthorizationRepository.findByUserCodeValue(token))
                            .or(() -> redisOAuth2AuthorizationRepository.findByDeviceCodeValue(token))
                            .map(item -> parse(item, registeredClientRepository.findByClientId(item.getRegisteredClientId()))).orElse(null);
            default -> null;
        };
        // @formatter:on
    }


    @SneakyThrows
    private OAuth2Authorization parse(RedisOAuth2Authorization redisOAuth2Authorization, RegisteredClient registeredClient) {
        if (Objects.isNull(redisOAuth2Authorization)) return null;
        Assert.isTrue(ObjectUtil.isNull(registeredClient), () -> new BizException("客户端异常"));
        MapType mapType = MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient).id(redisOAuth2Authorization.getId()).principalName(redisOAuth2Authorization.getPrincipalName()).authorizationGrantType(new AuthorizationGrantType(redisOAuth2Authorization.getAuthorizationGrantType())).authorizedScopes(StringUtils.commaDelimitedListToSet(redisOAuth2Authorization.getAuthorizedScopes())).attributes(attributesConsumer -> attributesConsumer.putAll(parseMap(redisOAuth2Authorization.getAttributes(), mapType)));
        if (StringUtils.hasText(redisOAuth2Authorization.getState())) {
            builder.attribute(STATE, redisOAuth2Authorization.getState());
        }
        // access token
        parseAccessToken(builder, redisOAuth2Authorization, mapType);
        // refresh token
        parseRefreshToken(builder, redisOAuth2Authorization, mapType);
        // authorization code
        parseAuthorizationCode(builder, redisOAuth2Authorization, mapType);
        // oidc id token
        parseOidcIdToken(builder, redisOAuth2Authorization, mapType);
        // user code
        parseUserCode(builder, redisOAuth2Authorization, mapType);
        // device code
        parseDeviceCode(builder, redisOAuth2Authorization, mapType);
        return builder.build();
    }

    @SneakyThrows
    private RedisOAuth2Authorization convert(OAuth2Authorization authorization) {
        if (Objects.isNull(authorization)) return null;
        RedisOAuth2Authorization redisOAuth2Authorization = new RedisOAuth2Authorization();
        redisOAuth2Authorization.setId(authorization.getId());
        redisOAuth2Authorization.setRegisteredClientId(authorization.getRegisteredClientId());
        redisOAuth2Authorization.setPrincipalName(authorization.getPrincipalName());
        redisOAuth2Authorization.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        redisOAuth2Authorization.setAuthorizedScopes(StringUtils.collectionToDelimitedString(authorization.getAuthorizedScopes(), ","));
        redisOAuth2Authorization.setAttributes(MAPPER.writeValueAsString(authorization.getAttributes()));
        redisOAuth2Authorization.setState(authorization.getAttribute(STATE));
        // access token
        setAccessToken(authorization, redisOAuth2Authorization);
        // refresh token
        setRefreshToken(authorization, redisOAuth2Authorization);
        // authorization code
        setAuthorizationCode(authorization, redisOAuth2Authorization);
        // oidc id token
        setOidcIdToken(authorization, redisOAuth2Authorization);
        // user code
        setUserCode(authorization, redisOAuth2Authorization);
        // device code
        setDeviceCode(authorization, redisOAuth2Authorization);
        return redisOAuth2Authorization;
    }


    private Map<String, Object> parseMap(String json, MapType mapType) {
        try {
            return MAPPER.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            throw new SysException(e);
        }
    }

    private void parseAccessToken(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String accessTokenValue = redisOAuth2Authorization.getAccessTokenValue();
        if (ObjectUtil.isNotNull(accessTokenValue)) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessTokenValue, redisOAuth2Authorization.getAccessTokenIssuedAt(), redisOAuth2Authorization.getAccessTokenExpiresAt(), StringUtils.commaDelimitedListToSet(redisOAuth2Authorization.getAccessTokenScopes()));
            builder.token(accessToken, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getAccessTokenMetadata(), mapType)));
        }
    }

    private void parseRefreshToken(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String refreshTokenValue = redisOAuth2Authorization.getRefreshTokenValue();
        if (ObjectUtil.isNotNull(refreshTokenValue)) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(refreshTokenValue, redisOAuth2Authorization.getRefreshTokenIssuedAt(), redisOAuth2Authorization.getRefreshTokenExpiresAt());
            builder.token(refreshToken, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getRefreshTokenMetadata(), mapType)));
        }
    }

    private void parseAuthorizationCode(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String authorizationCodeValue = redisOAuth2Authorization.getAuthorizationCodeValue();
        if (ObjectUtil.isNotNull(authorizationCodeValue)) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(authorizationCodeValue, redisOAuth2Authorization.getAuthorizationCodeIssuedAt(), redisOAuth2Authorization.getAuthorizationCodeExpiresAt());
            builder.token(authorizationCode, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getAuthorizationCodeMetadata(), mapType)));
        }
    }

    private void parseOidcIdToken(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String oidcIdTokenValue = redisOAuth2Authorization.getOidcIdTokenValue();
        if (ObjectUtil.isNotNull(oidcIdTokenValue)) {
            OidcIdToken oidcIdToken = new OidcIdToken(oidcIdTokenValue, redisOAuth2Authorization.getOidcIdTokenIssuedAt(), redisOAuth2Authorization.getOidcIdTokenExpiresAt(), parseMap(redisOAuth2Authorization.getOidcIdTokenClaims(), mapType));
            builder.token(oidcIdToken, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getOidcIdTokenMetadata(), mapType)));
        }
    }

    private void parseUserCode(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String userCodeValue = redisOAuth2Authorization.getUserCodeValue();
        if (ObjectUtil.isNotNull(userCodeValue)) {
            OAuth2UserCode userCode = new OAuth2UserCode(userCodeValue, redisOAuth2Authorization.getUserCodeIssuedAt(), redisOAuth2Authorization.getUserCodeExpiresAt());
            builder.token(userCode, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getUserCodeMetadata(), mapType)));
        }
    }

    private void parseDeviceCode(OAuth2Authorization.Builder builder, RedisOAuth2Authorization redisOAuth2Authorization, MapType mapType) {
        String deviceCodeValue = redisOAuth2Authorization.getDeviceCodeValue();
        if (ObjectUtil.isNotNull(deviceCodeValue)) {
            OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(deviceCodeValue, redisOAuth2Authorization.getDeviceCodeIssuedAt(), redisOAuth2Authorization.getDeviceCodeExpiresAt());
            builder.token(deviceCode, metadata -> metadata.putAll(parseMap(redisOAuth2Authorization.getDeviceCodeMetadata(), mapType)));
        }
    }


    @SneakyThrows
    private void setAccessToken(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OAuth2AccessToken> token = authorization.getAccessToken();
        if (ObjectUtil.isNotNull(token)) {
            OAuth2AccessToken accessToken = token.getToken();
            redisOAuth2Authorization.setAccessTokenValue(accessToken.getTokenValue());
            redisOAuth2Authorization.setAccessTokenIssuedAt(accessToken.getIssuedAt());
            redisOAuth2Authorization.setAccessTokenExpiresAt(accessToken.getExpiresAt());
            redisOAuth2Authorization.setAccessTokenMetadata(MAPPER.writeValueAsString(token.getMetadata()));
            redisOAuth2Authorization.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getScopes(), ","));
            redisOAuth2Authorization.setAccessTokenType(accessToken.getTokenType().getValue());
        }
    }

    @SneakyThrows
    private void setRefreshToken(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OAuth2RefreshToken> token = authorization.getRefreshToken();
        if (ObjectUtil.isNotNull(token)) {
            OAuth2RefreshToken refreshToken = token.getToken();
            redisOAuth2Authorization.setRefreshTokenValue(refreshToken.getTokenValue());
            redisOAuth2Authorization.setRefreshTokenIssuedAt(refreshToken.getIssuedAt());
            redisOAuth2Authorization.setRefreshTokenExpiresAt(refreshToken.getExpiresAt());
            redisOAuth2Authorization.setRefreshTokenMetadata(MAPPER.writeValueAsString(token.getMetadata()));
        }
    }

    @SneakyThrows
    private void setAuthorizationCode(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> token = authorization.getToken(OAuth2AuthorizationCode.class);
        if (ObjectUtil.isNotNull(token)) {
            OAuth2AuthorizationCode authorizationCode = token.getToken();
            redisOAuth2Authorization.setAuthorizationCodeValue(authorizationCode.getTokenValue());
            redisOAuth2Authorization.setAuthorizationCodeIssuedAt(authorizationCode.getIssuedAt());
            redisOAuth2Authorization.setAuthorizationCodeExpiresAt(authorizationCode.getExpiresAt());
            redisOAuth2Authorization.setAuthorizationCodeMetadata(MAPPER.writeValueAsString(token.getMetadata()));
        }
    }

    @SneakyThrows
    private void setOidcIdToken(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OidcIdToken> token = authorization.getToken(OidcIdToken.class);
        if (ObjectUtil.isNotNull(token)) {
            OidcIdToken oidcIdToken = token.getToken();
            redisOAuth2Authorization.setOidcIdTokenValue(oidcIdToken.getTokenValue());
            redisOAuth2Authorization.setOidcIdTokenIssuedAt(oidcIdToken.getIssuedAt());
            redisOAuth2Authorization.setOidcIdTokenExpiresAt(oidcIdToken.getExpiresAt());
            redisOAuth2Authorization.setOidcIdTokenMetadata(MAPPER.writeValueAsString(token.getMetadata()));
            redisOAuth2Authorization.setOidcIdTokenClaims(MAPPER.writeValueAsString(oidcIdToken.getClaims()));
        }
    }

    @SneakyThrows
    private void setUserCode(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OAuth2UserCode> token = authorization.getToken(OAuth2UserCode.class);
        if (ObjectUtil.isNotNull(token)) {
            OAuth2UserCode userCode = token.getToken();
            redisOAuth2Authorization.setUserCodeValue(userCode.getTokenValue());
            redisOAuth2Authorization.setUserCodeIssuedAt(userCode.getIssuedAt());
            redisOAuth2Authorization.setUserCodeExpiresAt(userCode.getExpiresAt());
            redisOAuth2Authorization.setUserCodeMetadata(MAPPER.writeValueAsString(token.getMetadata()));
        }
    }

    @SneakyThrows
    private void setDeviceCode(OAuth2Authorization authorization, RedisOAuth2Authorization redisOAuth2Authorization) {
        OAuth2Authorization.Token<OAuth2DeviceCode> token = authorization.getToken(OAuth2DeviceCode.class);
        if (ObjectUtil.isNotNull(token)) {
            OAuth2DeviceCode deviceCode = token.getToken();
            redisOAuth2Authorization.setDeviceCodeValue(deviceCode.getTokenValue());
            redisOAuth2Authorization.setDeviceCodeIssuedAt(deviceCode.getIssuedAt());
            redisOAuth2Authorization.setDeviceCodeExpiresAt(deviceCode.getExpiresAt());
            redisOAuth2Authorization.setDeviceCodeMetadata(MAPPER.writeValueAsString(token.getMetadata()));
        }
    }

}
