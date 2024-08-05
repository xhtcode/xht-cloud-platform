package com.xht.cloud.framework.exception.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：用户异常处理
 * <h3> 10000到 19999 段</h3>
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum UserErrorStatusCode implements IErrorStatusCode {

    //------------------------------用户校验------------------------------
    NO_LOGIN(900000, "用户凭证已过期"),

    LOGIN_USER_ERROR(900001, "账号不存在或密码错误"),

    AUTHENTICATION_FAILURE(900002, "用户认证异常"),

    USER_STATUS_EXCEPTION(900003, "用户状态异常"),

    USER_LOCKED(900004, "用户已锁定"),

    USER_BLACKLIST(900005, "用户黑名单"),

    INSUFFICIENT_AUTHORITY(900006, "数据访问权限不足"),


    OTHER_DEVICE_LOGIN(900007, "异地登录"),


    SYSTEM_ERROR(900020, "系统异常"),


    ;

    private final Integer code;

    private final String message;


}
