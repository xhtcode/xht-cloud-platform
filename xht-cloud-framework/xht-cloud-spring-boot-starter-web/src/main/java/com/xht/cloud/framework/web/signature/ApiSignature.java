package com.xht.cloud.framework.web.signature;

import java.lang.annotation.*;

/**
 * 描述 ：api 接口签名注解
 *
 * @author 小糊涂
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSignature {
}
