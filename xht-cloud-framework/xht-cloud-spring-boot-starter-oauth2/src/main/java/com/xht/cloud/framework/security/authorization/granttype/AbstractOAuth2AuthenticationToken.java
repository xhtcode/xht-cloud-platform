package com.xht.cloud.framework.security.authorization.granttype;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * 描述 ： 抽象  令牌
 *
 * @author 小糊涂
 **/
@Getter
public abstract class AbstractOAuth2AuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final AuthorizationGrantType authorizationGrantType;

    /**
     * @param authorizationGrantType 授权方式
     * @param clientPrincipal        经过身份验证的客户端主体
     * @param additionalParameters   附加参数
     */
    protected AbstractOAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
        this.authorizationGrantType = authorizationGrantType;
    }

}
