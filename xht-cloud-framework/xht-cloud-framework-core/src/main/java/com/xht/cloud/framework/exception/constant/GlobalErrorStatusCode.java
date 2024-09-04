package com.xht.cloud.framework.exception.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：全局 状态码
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum GlobalErrorStatusCode implements IErrorStatusCode {

    SUCCESS(200, "成功"),

    BAD_REQUEST(400, "错误的请求"),

    UNAUTHORIZED(401, "认证失败"),

    FORBIDDEN(403, "权限不足"),

    NOT_FOUND(404, "为查询到请求资源！"),

    ERROR(DEFAULT_ERROR_CODE, "服务器去旅行了，请稍后重试"),

    PARAM_INVALID(415, "无效的参数"),
    NOT_IMPLEMENTED(1000, "功能未实现/未开启"),
    ERROR_CONFIGURATION(1001, "错误的配置项"),

    USER_CREDENTIALS_EXPIRED(900000, "用户凭证已过期"),
   ;


    private final Integer code;

    private final String message;

}
