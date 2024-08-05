package com.xht.cloud.framework.security.authorization;

import com.xht.cloud.framework.security.authorization.log.LoginApplicationListener;
import com.xht.cloud.framework.security.authorization.password.OAuth2PasswordAuthenticationConverter;
import com.xht.cloud.framework.security.authorization.password.OAuth2PasswordAuthenticationProvider;
import com.xht.cloud.framework.security.authorization.token.TokenEndpointAuthenticationFailureHandler;
import com.xht.cloud.framework.security.authorization.token.TokenEndpointAuthenticationSuccessHandler;
import com.xht.cloud.framework.security.core.CustomDaoAuthenticationProvider;
import com.xht.cloud.framework.security.core.SecurityConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class AuthorizationServerAutoConfiguration {

    private final OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator;


    private final SecurityConfigProperties securityConfigProperties;

    private static void customize(OAuth2ResourceServerConfigurer<HttpSecurity> resourceServer) {
        resourceServer.jwt(Customizer.withDefaults());
    }

    /**
     * oauth2 个性化配置
     * 协议端点的 Spring 安全过滤器链。
     *
     * @param http                        {@link HttpSecurity }
     * @param registeredClientRepository  客户端存储 {@link RegisteredClientRepository}
     * @param authorizationService        授权信息存储 {@link OAuth2AuthorizationService}
     * @param authorizationConsentService 授权许可存储 {@link OAuth2AuthorizationConsentService}
     * @param authorizationServerSettings 授权服务器设置 {@link AuthorizationServerSettings}
     * @return {@link SecurityFilterChain}
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      RegisteredClientRepository registeredClientRepository,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2AuthorizationConsentService authorizationConsentService,
                                                                      AuthorizationServerSettings authorizationServerSettings) throws Exception {
        log.info("加载授权服务器设置\tAuthorizationServiceOAuth2AuthorizationService: {}", authorizationServerSettings.getClass());
        log.info("加载客户端\tRegisteredClientRepository: {}", registeredClientRepository.getClass());
        log.info("加载授权管理\tOAuth2AuthorizationService: {}", authorizationService.getClass());
        log.info("加载授权许可管理\tOAuth2AuthorizationConsentService: {}", authorizationConsentService.getClass());
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        OAuth2AuthorizationServerConfigurer serverConfigurer = http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
        // 用于管理新客户和现有客户的RegisteredClientRepository（必需）
        serverConfigurer.registeredClientRepository(registeredClientRepository);
        // 用于管理新的和现有的授权。
        serverConfigurer.authorizationService(authorizationService);
        // 用于管理新的和现有的授权许可
        serverConfigurer.authorizationConsentService(authorizationConsentService);
        // 用于自定义 OAuth2 授权服务器配置设置的AuthorizationServerSettings（必需）
        serverConfigurer.authorizationServerSettings(authorizationServerSettings);
        // 用于生成 OAuth2 授权服务器支持的令牌
        serverConfigurer.tokenGenerator(oAuth2TokenGenerator);
        // OAuth2 客户端身份验证的配置器
        serverConfigurer.clientAuthentication(clientAuthentication -> {
            // 客户端认证添加设备码的converter和provider
//            clientAuthentication.authenticationConverter(deviceClientAuthenticationConverter);
            clientAuthentication.errorResponseHandler((request, response, exception) -> System.out.println("deee"));
//            clientAuthentication.authenticationProvider(deviceClientAuthenticationProvider);
        });
        // OAuth2 授权端点的配置器 允许您自定义 OAuth2 授权请求的预处理、主处理和后处理逻辑
        serverConfigurer.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage("/oauth2/consent"));
        //OAuth2 设备授权端点的配置程序
        serverConfigurer.deviceAuthorizationEndpoint(deviceAuthorizationEndpoint -> deviceAuthorizationEndpoint.verificationUri("/activate"));
        //OAuth2 设备验证端点的配置程序
        serverConfigurer.deviceVerificationEndpoint(deviceVerificationEndpoint -> deviceVerificationEndpoint.consentPage("/oauth2/consent"));
        // OAuth2 令牌端点的配置器 允许您自定义 OAuth2 访问令牌请求的预处理、主处理和后处理逻辑。
        serverConfigurer.tokenEndpoint(tokenEndpoint -> {
            if (securityConfigProperties.getPassword().isEnabled()) {
                tokenEndpoint.accessTokenRequestConverter(new OAuth2PasswordAuthenticationConverter())
                        .authenticationProvider(new OAuth2PasswordAuthenticationProvider(authorizationService, oAuth2TokenGenerator));
            }
            tokenEndpoint.accessTokenResponseHandler(new TokenEndpointAuthenticationSuccessHandler());
            tokenEndpoint.errorResponseHandler(new TokenEndpointAuthenticationFailureHandler());
        });
        // OAuth2 令牌侦测端点的配置程序。
        serverConfigurer.tokenIntrospectionEndpoint(tokenIntrospectionEndpoint -> {
        });
        //OAuth2 令牌注销端点的配置器。
        serverConfigurer.tokenRevocationEndpoint(tokenRevocationEndpoint -> {
            //令牌吊销成功时调用
            //  tokenRevocationEndpoint.revocationResponseHandler(new TokenRevocationAuthenticationSuccessHandler());
            //用于处理和返回 OAuth2Error 响应的（后处理器）。
            // tokenRevocationEndpoint.errorResponseHandler(new TokenRevocationAuthenticationFailureHandler());
        });
        // OAuth2 授权服务器元数据端点的配置器。
        serverConfigurer.authorizationServerMetadataEndpoint(authorizationServerMetadataEndpoint -> {
        });
        // OAuth2 oidc
        serverConfigurer.oidc(oidc -> oidc
                // OpenID Connect 1.0 提供者配置端点的配置器。
                .providerConfigurationEndpoint(providerConfigurationEndpointConfigurer -> {
                })
                //OpenID Connect 1.0 注销端点的配置程序。
                .logoutEndpoint(logoutEndpoint -> {
                })
                // OpenID Connect 1.0 UserInfo 端点的配置器。
                .userInfoEndpoint(userInfoEndpoint -> {
                })
                // OpenID Connect 1.0 客户端注册端点的配置器。
                .clientRegistrationEndpoint(clientRegistrationEndpoint -> {
                })

        );
        //方法进行身份验证时重定向到登录页
        //授权端点
        http.exceptionHandling((exceptions) ->
                exceptions.defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML))
        );
        http.authenticationProvider(new CustomDaoAuthenticationProvider());
        // 处理使用access token访问用户信息端点和客户端注册端点
        http.oauth2ResourceServer(AuthorizationServerAutoConfiguration::customize);
        return http.build();
    }

    /**
     * 用于配置 Spring Authorization Server 的 AuthorizationServerSettings 实例。
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("https://example.com")
                .authorizationEndpoint("/oauth2/authorize")
                .deviceAuthorizationEndpoint("/oauth2/device_authorization")
                .deviceVerificationEndpoint("/oauth2/device_verification")
                .tokenEndpoint("/oauth2/token")
                .jwkSetEndpoint("/oauth2/jwks")
                .tokenRevocationEndpoint("/oauth2/revoke")
                .tokenIntrospectionEndpoint("/oauth2/introspect")
                .oidcClientRegistrationEndpoint("/connect/register")
                .oidcUserInfoEndpoint("/userinfo")
                .oidcLogoutEndpoint("/connect/logout")
                .build();
    }


    @Bean
    public LoginApplicationListener loginApplicationListener() {
        return new LoginApplicationListener();
    }
}
