package com.xht.cloud.framework.security.authorization.granttype;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 ： 抽象 认证转换器
 *
 * @author 小糊涂
 **/
public abstract class AbstractAuthenticationConverter implements AuthenticationConverter {

    /**
     * 转换器
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Authentication}
     */
    @Override
    public final Authentication convert(HttpServletRequest request) {
        // 请求链 FilterOrderRegistration
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!"GET".equals(request.getMethod()) && !getGrantType().equals(grantType)) {
            return null;
        }
        // 构建请求参数集合
        MultiValueMap<String, String> parameters = HttpServletUtils.getMultiValueMapParameters(request);
        List<String> scopes = parameters.get(OAuth2ParameterNames.SCOPE);
        // 判断scopes
        Assert.notEmpty(scopes, () -> new BizException("授权范围异常"));
        // 获取上下文认证信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> additionalParameters = new HashMap<>(parameters.size());
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.CLIENT_ID)) {
                additionalParameters.put(key, value.stream().findFirst());
            }
        });
        return convert(clientPrincipal, additionalParameters);
    }


    /**
     * 子类实现转换.
     *
     * @param authentication       认证参数
     * @param additionalParameters 扩展参数
     */
    protected abstract Authentication convert(Authentication authentication, Map<String, Object> additionalParameters);

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    protected abstract String getGrantType();

}
