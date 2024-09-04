package com.xht.cloud.framework.security.authorization.token;

import cn.hutool.core.map.MapUtil;
import com.xht.cloud.framework.security.domain.token.TokenResponse;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 描述 ：token 端点 成功时 响应
 *
 * @author 小糊涂
 **/
public class TokenEndpointAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =
            new OAuth2AccessTokenResponseHttpMessageConverter();

    /**
     * 当用户已成功通过身份验证时调用。
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication 认证过程过程中创建的<tt>Authentication</tt>对象 。
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                (OAuth2AccessTokenAuthenticationToken) authentication;
        TokenResponse tokenResponse = new TokenResponse();
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        tokenResponse.setAccessToken(accessToken.getTokenValue());
        tokenResponse.setTokenType(accessToken.getTokenType().getValue());
        tokenResponse.setScope(StringUtils.collectionToDelimitedString(accessToken.getScopes(), ","));
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            tokenResponse.setExpiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            tokenResponse.setRefreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            tokenResponse.setIdToken(MapUtil.getStr(additionalParameters, "id_token"));
            tokenResponse.setAdditionalParameters(MapUtil.removeAny(additionalParameters, "id_token"));
        }
        HttpServletUtils.writeString(response, R.ok(tokenResponse));
    }

}
