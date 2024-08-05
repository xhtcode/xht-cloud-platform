package com.xht.cloud.framework.security.authorization.password;

import com.xht.cloud.framework.security.constant.CustomAuthorizationGrantType;
import com.xht.cloud.framework.security.authorization.granttype.AbstractOAuth2AuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * 描述 ：密码模式token令牌
 *
 * @author 小糊涂
 **/
public class OAuth2PasswordAuthenticationToken extends AbstractOAuth2AuthenticationToken {
    protected OAuth2PasswordAuthenticationToken(Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(CustomAuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
    }
}
