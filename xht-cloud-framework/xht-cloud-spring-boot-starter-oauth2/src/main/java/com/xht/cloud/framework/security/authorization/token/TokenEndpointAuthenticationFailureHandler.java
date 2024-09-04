package com.xht.cloud.framework.security.authorization.token;

import com.xht.cloud.framework.security.convert.OAuth2ErrorParametersConverter;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.web.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Map;

/**
 * 描述 ：token生成成功 响应信息处理
 * token端点
 *
 * @author 小糊涂
 * @see org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter
 **/
@Slf4j
public class TokenEndpointAuthenticationFailureHandler implements AuthenticationFailureHandler {

    protected final Converter<OAuth2Error, Map<String, String>> errorParametersConverter;

    public TokenEndpointAuthenticationFailureHandler() {
        this.errorParametersConverter = new OAuth2ErrorParametersConverter();
        log.info("token 端点 当身份验证尝试失败时调用");
    }

    /**
     * 当身份验证尝试失败时调用。
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception 为拒绝身份验证而抛出的异常请求。
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OAuth2Error oauth2Error = ((OAuth2AuthenticationException) exception).getError();
        Map<String, String> errorParameters = this.errorParametersConverter.convert(oauth2Error);
        HttpServletUtils.writeString(response, HttpStatus.BAD_REQUEST, R.failed(oauth2Error.getDescription()).setData(errorParameters));
        log.error("token endpoint 异常 error={}", errorParameters, exception);
    }
}
