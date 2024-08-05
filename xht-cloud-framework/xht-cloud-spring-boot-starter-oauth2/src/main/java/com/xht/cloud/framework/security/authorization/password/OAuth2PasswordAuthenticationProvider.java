package com.xht.cloud.framework.security.authorization.password;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.api.log.enums.LoginStatusEnums;
import com.xht.cloud.framework.security.authorization.granttype.AbstractOAuth2AuthenticationProvider;
import com.xht.cloud.framework.security.authorization.log.LoginEvent;
import com.xht.cloud.framework.security.constant.CustomAuthorizationGrantType;
import com.xht.cloud.framework.security.domain.RequestUserBO;
import com.xht.cloud.framework.security.domain.UserDetailsBO;
import com.xht.cloud.framework.security.exception.CaptchaException;
import com.xht.cloud.framework.security.userdetails.IUserDetailsService;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;
import com.xht.cloud.framework.utils.web.HttpServletUtils;
import com.xht.cloud.framework.utils.web.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.xht.cloud.framework.exception.constant.UserErrorStatusCode.AUTHENTICATION_FAILURE;
import static com.xht.cloud.framework.exception.constant.UserErrorStatusCode.LOGIN_USER_ERROR;
import static com.xht.cloud.framework.security.constant.SecurityConstant.ERROR_URL;

/**
 * 描述 ：密码模式 认证处理器
 *
 * @author 小糊涂
 **/
@Slf4j
@Component
public class OAuth2PasswordAuthenticationProvider extends AbstractOAuth2AuthenticationProvider {

    public OAuth2PasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(CustomAuthorizationGrantType.PASSWORD, authorizationService, tokenGenerator);
        log.info("自定义授权模式：【密码模式】");
    }

    /**
     * @param userBuilder {@link RequestUserBO} 请求用户信息
     * @param registeredClient {@link RegisteredClient} 客户端信息
     * @return {@link Authentication}
     */
    @Override
    protected Authentication authenticateUserDetails(RequestUserBO userBuilder, RegisteredClient registeredClient) throws AuthenticationException {
        SysLoginLogDTO sysLoginLogDTO = new SysLoginLogDTO();
        Boolean saveLog = true;
        try {
            userBuilder.checkPassword().checkUserName().checkUserType().checkCaptcha();
            HttpServletRequest request = HttpServletUtils.getRequest();
            String ip = IPUtils.getIp(request);
            sysLoginLogDTO.setLoginType(this.authorizationGrantType.getValue());
            sysLoginLogDTO.setLoginTime(LocalDateTime.now());
            sysLoginLogDTO.setLoginIp(ip);
            sysLoginLogDTO.setLoginAddress(IPUtils.getRealAddress(ip));
            sysLoginLogDTO.setUserAgent(HttpServletUtils.getHeader(request, "user-agent"));
            sysLoginLogDTO.setUserType(userBuilder.getUserType().getDesc());
            sysLoginLogDTO.setUserAccount(userBuilder.getUsername());
            IUserDetailsService userDetailsService = getUserDetailsService(userBuilder.getUserType());
            UserDetailsBO userDetails = userDetailsService.loadUser(userBuilder);
            sysLoginLogDTO.setUserId(userDetails.getId());
            sysLoginLogDTO.setUserName(userDetails.getUsername());
            sysLoginLogDTO.setLoginStatus(LoginStatusEnums.SUCCESS);
            sysLoginLogDTO.setLoginDesc("登录成功");
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            sysLoginLogDTO.setLoginStatus(LoginStatusEnums.ERROR);
            sysLoginLogDTO.setLoginDesc(e.getMessage());
            throw new OAuth2AuthenticationException(new OAuth2Error(LOGIN_USER_ERROR.getCode().toString(), "账号或密码错误！", ERROR_URL), e);
        } catch (CaptchaException e) {
            saveLog = Boolean.FALSE;
            log.debug("[验证码错误时] 不保存登录日志！");
            throw new OAuth2AuthenticationException(new OAuth2Error(AUTHENTICATION_FAILURE.getCode().toString(), e.getMessage(), ERROR_URL), e);
        } catch (Exception e) {
            sysLoginLogDTO.setLoginStatus(LoginStatusEnums.ERROR);
            sysLoginLogDTO.setLoginDesc(e.getMessage());
            throw new OAuth2AuthenticationException(new OAuth2Error(AUTHENTICATION_FAILURE.getCode().toString(), e.getMessage(), ERROR_URL), e);
        } finally {
            userBuilder.clearCaptcha();
            if (saveLog) {
                SpringContextUtil.publishEvent(new LoginEvent(sysLoginLogDTO));
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
