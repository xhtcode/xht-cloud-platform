package com.xht.cloud.framework.security.resource;

import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.security.core.CustomDaoAuthenticationProvider;
import com.xht.cloud.framework.security.core.PermitAllUrlProperties;
import com.xht.cloud.framework.security.resource.introspection.GlobalOpaqueTokenIntrospector;
import com.xht.cloud.framework.security.web.Oauth2AuthenticationEntryPoint;
import com.xht.cloud.framework.security.web.UnAuthorizedAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;


/**
 * 描述 ：资源服务器 拦截器配置
 *
 * @author 小糊涂
 **/
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ResourceSecurityConfig {

    private final PermitAllUrlProperties permitAllUrlProperties;

    @Bean(name = "resourceServerSecurityFilter")
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,GlobalOpaqueTokenIntrospector globalOpaqueTokenIntrospector) throws Exception {
        http.authorizeHttpRequests(matcherRegistry -> {
                    matcherRegistry.requestMatchers(ArrayUtil.toArray(permitAllUrlProperties.getWhiteUrls(), String.class)).permitAll();
                    matcherRegistry.anyRequest().authenticated();
                })
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer.authenticationEntryPoint(new Oauth2AuthenticationEntryPoint());
                    resourceServer.opaqueToken(opaqueTokenConfigurer -> opaqueTokenConfigurer.introspector(globalOpaqueTokenIntrospector));
                })//资源服务器配置
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new Oauth2AuthenticationEntryPoint());//请求未认证的接口
                    exception.accessDeniedHandler(new UnAuthorizedAccessDeniedHandler());// 请求未授权的接口
                })
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // 清除session
                .logout(logout -> logout.clearAuthentication(true).invalidateHttpSession(true))
                .authenticationProvider(new CustomDaoAuthenticationProvider());
        return http.build();
    }


}
