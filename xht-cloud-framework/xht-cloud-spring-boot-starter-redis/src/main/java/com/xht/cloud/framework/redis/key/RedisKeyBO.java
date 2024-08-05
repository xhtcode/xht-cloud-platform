package com.xht.cloud.framework.redis.key;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ：redis key的业务类
 *
 * @author 小糊涂
 **/
@Getter
@Builder
public class RedisKeyBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * key名称
     */
    private final String keyName;

    /**
     * 过期时间
     */
    private final Long timeOut;

    /**
     * 过期时间
     */
    private final TimeUnit timeType;

    private RedisKeyBO(String keyName, Long timeOut, TimeUnit timeType) {
        this.keyName = keyName;
        this.timeOut = timeOut;
        this.timeType = timeType;
    }

    /**
     * 判断是否过期类型key
     */
    public boolean isExpireKey() {
        return Objects.isNull(timeOut) || Objects.isNull(timeType);
    }

}
