package com.xht.cloud.framework.security.resource.annotaion;

import com.xht.cloud.framework.core.rpc.RpcConstants;
import com.xht.cloud.framework.security.constant.SkipAuthTypeEnums;

import java.lang.annotation.*;

/**
 * 描述 ：跳过权限认证
 *
 * @author 小糊涂
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipAuthentication {

    /**
     * 调用方式
     */
    SkipAuthTypeEnums value() default SkipAuthTypeEnums.INNER;

    /**
     * 请求头的key
     */
    String headerKey() default RpcConstants.RPC_HEADER_KEY;
}
