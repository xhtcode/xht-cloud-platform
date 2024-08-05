package com.xht.cloud.framework.security;

import com.xht.cloud.framework.security.core.PermissionCheckService;
import com.xht.cloud.framework.security.core.SecurityConfigProperties;
import com.xht.cloud.framework.security.crypto.password.Md5PasswordEncoder;
import com.xht.cloud.framework.security.oauth2.server.authorization.RedisOAuth2AuthorizationService;
import com.xht.cloud.framework.security.repository.RedisOAuth2AuthorizationRepository;
import com.xht.cloud.framework.security.userdetails.AdminDetailsServiceImpl;
import com.xht.cloud.framework.security.userdetails.IUserDetailsService;
import com.xht.cloud.framework.security.userdetails.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * 描述 ：oauth2 认证配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@EnableRedisRepositories(basePackageClasses = RedisOAuth2AuthorizationRepository.class)
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class DefaultOAuth2AuthorizationConfig {

    @Bean
    public PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean(name = "oauth2")
    @ConditionalOnMissingBean(name = "oauth2")
    public PermissionCheckService permissionCheckService() {
        return new PermissionCheckService();
    }

    @Bean(name = "passwordUserDetailsService")
    public IUserDetailsService userDetailsServiceImpl() {
        log.info(">>>>>>oauth2-start UserDetailsServiceImpl<<<<<<");
        return new UserDetailsServiceImpl();
    }
    @Bean(name = "adminDetailsServiceImpl")
    public IUserDetailsService adminDetailsServiceImpl() {
        log.info(">>>>>>oauth2-start UserDetailsServiceImpl<<<<<<");
        return new AdminDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(RegisteredClientRepository.class)
    public RegisteredClientRepository registeredClientRepository2(JdbcTemplate jdbcTemplate) {
        log.info(">>>>>>oauth2-start JdbcRegisteredClientRepository<<<<<<");
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    @Bean
    @SuppressWarnings("all")
    @ConditionalOnMissingBean(OAuth2AuthorizationService.class)
    public OAuth2AuthorizationService oAuth2AuthorizationService(RegisteredClientRepository registeredClientRepository,
                                                                      RedisOAuth2AuthorizationRepository redisOAuth2AuthorizationRepository) {
        log.info(">>>>>>oauth2-start RedisOAuth2AuthorizationService<<<<<<");
        return new RedisOAuth2AuthorizationService(registeredClientRepository, redisOAuth2AuthorizationRepository);
    }

}
