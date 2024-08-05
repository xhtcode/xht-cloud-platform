package com.xht.cloud.gateway.constant;

import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ： 网关错误状态
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum GateWayErrorStatusCode implements IErrorStatusCode {
    // @formatter:off
    BAD_GATEWAY(300001, "错误网关"),

    BAD_REQUEST(300002, "错误请求"),

    TOO_MANY_REQUESTS(300003, "请求频繁"),

    NOT_FOUND(300004, "访问地址不存在"),

    SERVICE_UNAVAILABLE(300005, "服务正在维护"),

    INTERNAL_SERVER_ERROR(300006, "服务器内部错误，无法完成请求"),
    ;
    // @formatter:on
    private final Integer code;

    private final String message;

}
