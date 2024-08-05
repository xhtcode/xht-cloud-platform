package com.xht.cloud.auth.config;

import com.xht.cloud.framework.security.web.FormLoginAuthenticationFailureHandler;
import com.xht.cloud.framework.security.web.FormLoginAuthenticationSuccessHandler;
import com.xht.cloud.framework.security.web.Oauth2AuthenticationEntryPoint;
import com.xht.cloud.framework.security.web.UserLogoutSuccessHandler;
import com.xht.cloud.framework.security.web.session.OAuth2SessionInformationExpiredStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 用于身份验证的 Spring Security 过滤器链。
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/error", "/actuator/**","/code").permitAll()
                                .anyRequest().authenticated())
                .formLogin(formLoginConfigurer -> {
                    formLoginConfigurer.failureHandler(new FormLoginAuthenticationFailureHandler());//认证失败时的处理
                    formLoginConfigurer.successHandler(new FormLoginAuthenticationSuccessHandler());//认证成功时的处理
                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer.logoutSuccessHandler(new UserLogoutSuccessHandler());//注销成功时的处理
                })
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(new Oauth2AuthenticationEntryPoint());//请求未认证的接口
                })
                .sessionManagement(session -> {
                    session
                            .maximumSessions(1) // 设置每个用户的最大并发会话数为
                            .expiredSessionStrategy(new OAuth2SessionInformationExpiredStrategy());
                })
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

}