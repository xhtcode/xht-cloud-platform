package com.xht.cloud.framework.security.constant;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

/**
 * 描述 ： security 常量
 *
 * @author 小糊涂
 **/
public interface SecurityConstant {

    /**
     * 请求的`类型
     */
    String REQUEST_USER_TYPE = "userType";

    /**
     * 请求的`验证码 uuid`name值
     */
    String REQUEST_CAPTCHA_UUID = "uuid";

    /**
     * 请求的`验证码 信息`name值
     */
    String REQUEST_CAPTCHA_CODE = "captcha";

    /**
     * 请求的`账号`name值
     */
    String REQUEST_USERNAME = "userName";

    /**
     * 请求的`密码`name值
     */
    String REQUEST_PASSWORD = "passWord";

    /**
     * 请求的范围
     */
    String REQUEST_SCOPE = "scope";

    /**
     * 请求的范围分隔符
     */
    String REQUEST_SCOPE_SPLIT = ",";

    /**
     * 角色前缀
     */
    String ROLE_PREFIX = "ROLE_";


    /**
     * {@code error_description} - 在授权响应和访问令牌响应中使用。
     *
     * @see OAuth2ParameterNames
     */
    String ERROR_CODE = "error_code";

    /**
     * {@code error_description} - 在授权响应和访问令牌响应中使用。
     *
     * @see OAuth2ParameterNames
     */
    String ERROR_DESCRIPTION = "error_description";

    /**
     * {@code error_uri} - 在授权响应和访问令牌响应中使用
     *
     * @see OAuth2ParameterNames
     */
    String ERROR_URI_CODE = "error_uri";


    /**
     * 刷新令牌有效期默认 30 天
     */
    int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;
    String ERROR_URL = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
}
