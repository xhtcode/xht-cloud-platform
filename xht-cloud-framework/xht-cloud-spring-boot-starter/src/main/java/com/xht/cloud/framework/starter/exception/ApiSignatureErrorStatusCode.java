package com.xht.cloud.framework.starter.exception;

import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：签名验签失败错误码
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum ApiSignatureErrorStatusCode implements IErrorStatusCode {

    // @formatter:off

    API_ID_NOT_FOUNT(100001, "查询不到应用标识"),

    API_ID_INVALID(100001, "无效的应用标识"),

    API_KEY_NOT_FOUNT(100002, "应用`app-key`匹配失败!"),

    API_KEY_ERROR(100002, "应用`app-key`匹配失败!"),

    TIMESTAMP_TIME_OUT(100003, "timestamp 已过期"),

    SIGNATURE_ERROR(100004, "签名错误"),

   ;
    // @formatter:on
    private final Integer code;

    private final String message;

}