package com.xht.cloud.framework.security.authorization.granttype;

import cn.hutool.core.util.IdUtil;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.exception.constant.UserErrorStatusCode;
import com.xht.cloud.framework.security.domain.RequestUserBO;
import com.xht.cloud.framework.security.userdetails.IUserDetailsService;
import com.xht.cloud.framework.security.utils.OAuth2AuthenticationProviderUtils;
import com.xht.cloud.framework.security.utils.OAuth2ExceptionUtils;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.xht.cloud.framework.security.utils.OAuth2AuthenticationProviderUtils.*;

/**
 * 描述 ：抽象认证处理器
 *
 * @author 小糊涂
 **/
@Slf4j
public abstract class AbstractOAuth2AuthenticationProvider implements AuthenticationProvider {

    protected final AuthorizationGrantType authorizationGrantType;
    protected final OAuth2AuthorizationService authorizationService;
    protected final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public AbstractOAuth2AuthenticationProvider(AuthorizationGrantType authorizationGrantType, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authorizationGrantType = authorizationGrantType;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractOAuth2AuthenticationToken oAuth2AuthenticationToken = (AbstractOAuth2AuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientAuthenticationToken = getAuthenticatedClientElseThrowInvalidClient(oAuth2AuthenticationToken);
        RegisteredClient registeredClient = clientAuthenticationToken.getRegisteredClient();
        if (Objects.isNull(registeredClient) || !registeredClient.getAuthorizationGrantTypes().contains(this.authorizationGrantType)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
        RequestUserBO requestUserBO = RequestUserBO.builderUser(oAuth2AuthenticationToken.getAdditionalParameters());
        requestUserBO.checkScopes(registeredClient.getScopes());
        Authentication principal = authenticateUserDetails(requestUserBO, registeredClient);
        Set<String> requestScopes = requestUserBO.getScopes();
        // @formatter:off
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(principal.getName())
                .authorizationGrantType(authorizationGrantType)
                .authorizedScopes(requestScopes)
                .attribute(Principal.class.getName(), principal);
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient).principal(principal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(requestScopes)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(authorizationGrantType)
                .authorizationGrant(oAuth2AuthenticationToken);
        // @formatter:on
        // ----- Access token -----
        OAuth2AccessToken accessToken
                = createOAuth2AccessToken(tokenContextBuilder, authorizationBuilder, tokenGenerator);

        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken
                = creatOAuth2RefreshToken(tokenContextBuilder, authorizationBuilder, tokenGenerator, clientAuthenticationToken, registeredClient);
        // ----- ID token -----
        OidcIdToken idToken = OAuth2AuthenticationProviderUtils.createOidcIdToken(
                tokenContextBuilder,
                authorizationBuilder,
                this.tokenGenerator
        );
        authorizationBuilder.id(IdUtil.getSnowflakeNextIdStr());
        OAuth2Authorization authorization = authorizationBuilder.build();
        authorizationService.save(authorization);
        log.debug("[密码模式 provider] |- Resource Owner Password returning OAuth2AccessTokenAuthenticationToken.");
        Map<String, Object> additionalParameters = OAuth2AuthenticationProviderUtils.idTokenAdditionalParameters(idToken);
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientAuthenticationToken, accessToken, refreshToken, additionalParameters);
    }


    /**
     * 身份验证
     *
     * @param requestUserBO    用户提交的信息
     * @param registeredClient 客户端信息
     * @return 身份验证信息
     * @throws AuthenticationException 身份验证异常
     */
    protected abstract Authentication authenticateUserDetails(RequestUserBO requestUserBO, RegisteredClient registeredClient) throws AuthenticationException;

    /**
     * @param userType 登录类型
     * @return {@link IUserDetailsService}
     */
    protected final IUserDetailsService getUserDetailsService(UserTypeEnums userType) {
        Map<String, IUserDetailsService> userDetailsServiceMap = SpringContextUtil.getBeansOfType(IUserDetailsService.class);
        IUserDetailsService iUserDetailsService = userDetailsServiceMap.values()
                .stream()
                .filter(service -> service.support(this.authorizationGrantType.getValue(), userType)).findFirst().orElse(null);
        if (Objects.isNull(iUserDetailsService)) {
            throw OAuth2ExceptionUtils.throwError(UserErrorStatusCode.SYSTEM_ERROR);
        }
        return iUserDetailsService;
    }
}
