package com.xht.cloud.framework.mybatis.tool;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureBO;

import java.util.concurrent.TimeUnit;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class ApiSignatureCache {
    private static final Cache<String, ApiSignatureBO> API_SIGNATURE_CACHE
            //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置写缓存后30分钟过期
            .expireAfterWrite(30, TimeUnit.MINUTES)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
            //设置要统计缓存的命中率
            .recordStats().build();

    public static ApiSignatureBO get(String appId) {
        return API_SIGNATURE_CACHE.getIfPresent(String.format("xht:cloud:api:%s", appId));
    }

    public static void remove(String appId) {
        API_SIGNATURE_CACHE.invalidate(String.format("xht:cloud:api:%s", appId));
    }


    public static void set(ApiSignatureBO apiSignatureBO) {
        API_SIGNATURE_CACHE.put(String.format("xht:cloud:api:%s", apiSignatureBO.getAppId()), apiSignatureBO);
    }
}
