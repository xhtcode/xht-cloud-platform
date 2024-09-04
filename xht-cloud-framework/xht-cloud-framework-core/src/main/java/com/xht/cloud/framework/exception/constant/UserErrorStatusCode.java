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
    USER_CREDENTIALS_EXPIRED(900000, "用户凭证已过期"),

    ;

    private final Integer code;

    private final String message;


}
