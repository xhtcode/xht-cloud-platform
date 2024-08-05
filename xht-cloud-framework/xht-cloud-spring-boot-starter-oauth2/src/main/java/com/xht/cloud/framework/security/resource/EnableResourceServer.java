package com.xht.cloud.framework.security.resource;

import com.xht.cloud.framework.core.rpc.RpcConstants;
import com.xht.cloud.framework.security.constant.SkipAuthTypeEnums;
import com.xht.cloud.framework.security.resource.introspection.GlobalOpaqueTokenIntrospector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述 ：资源服务器启动器
 *
 * @author 小糊涂
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {GlobalOpaqueTokenIntrospector.class, ResourceSecurityConfig.class})
public @interface EnableResourceServer {

    SkipAuthTypeEnums value() default SkipAuthTypeEnums.INNER;

    String headerName() default RpcConstants.RPC_HEADER_KEY;
}
