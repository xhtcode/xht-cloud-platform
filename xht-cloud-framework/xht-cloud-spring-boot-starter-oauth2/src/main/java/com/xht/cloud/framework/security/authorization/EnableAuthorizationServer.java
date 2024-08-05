package com.xht.cloud.framework.security.authorization;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述 ：授权服务器启动器
 *
 * @author 小糊涂
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {AuthorizationServerSupportAutoConfiguration.class, AuthorizationServerAutoConfiguration.class})
public @interface EnableAuthorizationServer {
}
