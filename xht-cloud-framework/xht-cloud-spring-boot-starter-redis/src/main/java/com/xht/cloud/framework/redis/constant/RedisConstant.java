package com.xht.cloud.framework.redis.constant;

/**
 * 描述 ：redis常用常量
 *
 * @author 小糊涂
 **/
public interface RedisConstant {

    /**
     * redis key值 分割
     */
    String KEY_DELIMITED = ":";

    /**
     * 布隆过滤器Key.
     */
    String API_SIGNATURE_NONCE_KEY = "xht:signature:filter:{}";

}
