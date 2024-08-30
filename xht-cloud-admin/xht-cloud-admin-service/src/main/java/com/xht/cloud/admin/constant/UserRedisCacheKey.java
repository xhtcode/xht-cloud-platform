package com.xht.cloud.admin.constant;

/**
 * 描述 ：用户缓存的key
 *
 * @author : 小糊涂
 **/
public interface UserRedisCacheKey {

    /**
     * 用户-工作人员 信息的key
     */
    String STAFF_USER_INFO_KEY = "user:staff:info:{}";

    /**
     * 用户-管理员 信息的key
     */
    String ADMIN_USER_INFO_KEY = "user:admin:info:{}";
}
