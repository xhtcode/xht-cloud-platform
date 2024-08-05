package com.xht.cloud.framework.security.resource.introspection;

import cn.hutool.core.util.ObjectUtil;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.utils.OAuth2ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.xht.cloud.framework.exception.constant.UserErrorStatusCode.NO_LOGIN;

/**
 * 描述 ：提供自定义OpaqueTokenIntrospector
 *
 * @author 小糊涂
 **/
@Slf4j
@RequiredArgsConstructor
public class GlobalOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    /**
     * 内省并验证给定的令牌，返回其属性。
     * <p>
     *
     * @param token 内省的令牌
     * @return 令牌的属性
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        // 用户相关数据，低命中率且数据庞大放redis稳妥，分布式集群需要通过redis实现数据共享
        String userInfoKey = String.format("oauth2:token:%s", token);
        UserDetailsBO obj = (UserDetailsBO) redisTemplate.opsForValue().get(userInfoKey);
        if (Objects.nonNull(obj)) {
            return obj;
        }
        OAuth2Authorization authorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (ObjectUtil.isNull(authorization)) {
            throw OAuth2ExceptionUtils.throwError(NO_LOGIN);
        }
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        Instant expiresAt = accessToken.getToken().getExpiresAt();
        Instant nowAt = Instant.now();
        long expireTime = ChronoUnit.SECONDS.between(nowAt, expiresAt);
        // 10秒后过期
        long minTime = 10;
        if (expireTime > minTime) {
            Object principal = ((UsernamePasswordAuthenticationToken) Objects
                    .requireNonNull(authorization.getAttribute(Principal.class.getName()))).getPrincipal();
            UserDetailsBO userDetail = (UserDetailsBO) principal;
            redisTemplate.opsForValue().set(userInfoKey, userDetail, expireTime - 5, TimeUnit.SECONDS);
            return userDetail;
        }
        throw OAuth2ExceptionUtils.throwError(NO_LOGIN);
    }

}
