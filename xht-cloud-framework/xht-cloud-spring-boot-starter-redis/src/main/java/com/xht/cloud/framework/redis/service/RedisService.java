package com.xht.cloud.framework.redis.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 描述 ：redis 缓存工具类
 *
 * @author 小糊涂
 **/
public interface RedisService {

    /**
     * 获取key的过期时间
     *
     * @param key redis key
     * @return 过期时间
     */
    Long getExpire(String key);

    /**
     * 缓存数据 超时类型是 秒
     *
     * @param key     redis key
     * @param timeout 超时时长 默认毫秒
     * @param data    数据
     * @return 数据
     */
    default <T> T get(String key, long timeout, Supplier<T> data) {
        return get(key, timeout, TimeUnit.MILLISECONDS, data);
    }


    /**
     * 缓存数据
     *
     * @param key     redis key
     * @param timeout 超时时长
     * @param unit    超时类型
     * @param data    数据
     * @return 数据
     */
    <T> T get(String key, long timeout, TimeUnit unit, Supplier<T> data);

    /**
     * 缓存数据
     *
     * @param key redis key
     * @return 数据
     */
    Object get(String key);

    /**
     * 存储一个redis数据
     *
     * @param key   key
     * @param value value
     */
    void set(String key, Object value);

    /**
     * 存储一个会过期的redis数据
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时时长
     * @param unit    超时类型
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 删除key
     *
     * @param key redis key
     * @return boolean true成功
     */
    boolean delete(String key);

    /**
     * 删除key
     *
     * @param keys redis key
     * @return boolean true成功
     */
    Long delete(Collection<String> keys);
}
