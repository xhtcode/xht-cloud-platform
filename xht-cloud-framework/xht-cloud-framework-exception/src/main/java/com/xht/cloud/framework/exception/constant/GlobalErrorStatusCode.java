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

    // @formatter:off

    SUCCESS(200,"成功"),

    BAD_REQUEST(400,"错误请求"),

    PARAMS_ERROR(400,"请求参数不正确"),

    UNAUTHORIZED(401,"未授权"),

    FORBIDDEN(403,"服务器拒绝请求"),

    SYSTEM_FORBIDDEN(403,"系统资源(权限不足)"),

    NOT_FOUND(404,"请求未找到"),

    METHOD_NOT_ALLOWED(405,"请求方法不正确"),

    LOCKED(423,"请求失败，请稍后重试"),

    TOO_MANY_REQUESTS(423,"请求过于频繁，请稍后重试"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    NOT_IMPLEMENTED(501, "功能未实现/未开启"),

    ERROR_CONFIGURATION(502, "错误的配置项"),

    DEMO_DENY(901, "演示模式，禁止写操作"),
   ;

    // @formatter:on
    private final Integer code;

    private final String message;


}
