package com.xht.cloud.framework.security.constant;

import com.xht.cloud.framework.security.exception.OAuth2Exception;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 描述 ： 自定义授权模式
 *
 * @author 小糊涂
 **/
public final class CustomAuthorizationGrantType {

    /**
     * 密码模式
     */
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");

    public static AuthorizationGrantType getGrantType(String grantType) {
        if (StringUtils.isEmpty(grantType)) throw new OAuth2Exception("grantType is empty!");
        return PASSWORD;
    }
}
