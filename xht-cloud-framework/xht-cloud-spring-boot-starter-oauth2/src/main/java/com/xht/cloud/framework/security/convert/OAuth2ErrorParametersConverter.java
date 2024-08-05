package com.xht.cloud.framework.security.convert;

import com.xht.cloud.framework.security.constant.SecurityConstant;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：重写
 * 原类在 {@link org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter}的静态类{@code OAuth2ErrorParametersConverter}
 *
 * @author 小糊涂
 **/
public final class OAuth2ErrorParametersConverter implements Converter<OAuth2Error, Map<String, String>> {

    @Override
    public Map<String, String> convert(@Nullable OAuth2Error oauth2Error) {
        Map<String, String> parameters = new HashMap<>();
        assert oauth2Error != null;
        parameters.put(SecurityConstant.ERROR_CODE, oauth2Error.getErrorCode());
        if (StringUtils.hasText(oauth2Error.getDescription())) {
            parameters.put(SecurityConstant.ERROR_DESCRIPTION, oauth2Error.getDescription());
        }
        if (StringUtils.hasText(oauth2Error.getUri())) {
            parameters.put(SecurityConstant.ERROR_URI_CODE, oauth2Error.getUri());
        }
        return parameters;
    }

}
