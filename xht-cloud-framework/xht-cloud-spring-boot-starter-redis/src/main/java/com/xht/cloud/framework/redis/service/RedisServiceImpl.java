package com.xht.cloud.framework.redis.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 描述 ：redis 缓存工具类
 *
 * @author 小糊涂
 **/
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        Assert.notNull(redisTemplate, "[Assertion failed] - this redisTemplate is required; it must not be null");
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取key的过期时间
     *
     * @param key redis key
     * @return 过期时间
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @param key     redis key
     * @param timeout 超时时常
     * @param unit    超时类型
     * @param data    数据
     * @return T
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, long timeout, TimeUnit unit, Supplier<T> data) {
        T redisData = (T) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(redisData)) {
            return redisData;
        }
        T t = data.get();
        redisTemplate.opsForValue().set(key, t, timeout, unit);
        return t;
    }

    /**
     * 缓存数据
     *
     * @param key redis key
     * @return 数据
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储一个会过期的redis数据
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时时长
     * @param unit    超时类型
     */
    @Override
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 删除key
     *
     * @param key redis key
     * @return boolean true成功
     */
    @Override
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除key
     *
     * @param keys redis key
     * @return boolean true成功
     */
    @Override
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }
}
