package com.xht.cloud.framework.security.convert;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.security.constant.OAuth2ErrorStatusCode;
import com.xht.cloud.framework.security.domain.redis.RedisOAuth2Authorization;
import com.xht.cloud.framework.security.utils.OAuth2ExceptionUtils;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Map;
import java.util.Objects;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.STATE;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class OAuth2AuthorizationConvert {


}
