package com.xht.cloud.framework.security.authorization.password;

import com.xht.cloud.framework.security.constant.CustomAuthorizationGrantType;
import com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter;
import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * 描述 ：密码模式 身份验证转换器
 *
 * @author 小糊涂
 **/
public class OAuth2PasswordAuthenticationConverter extends AbstractAuthenticationConverter {

    /**
     * 子类实现转换.
     *
     * @param authentication       认证参数
     * @param additionalParameters 扩展参数
     */
    @Override
    public Authentication convert(Authentication authentication, Map<String, Object> additionalParameters) {
        return new OAuth2PasswordAuthenticationToken(authentication, additionalParameters);
    }

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    @Override
    public String getGrantType() {
        return CustomAuthorizationGrantType.PASSWORD.getValue();
    }
}
