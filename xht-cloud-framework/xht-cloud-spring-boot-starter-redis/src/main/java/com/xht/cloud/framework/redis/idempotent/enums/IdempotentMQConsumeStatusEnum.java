package com.xht.cloud.framework.redis.idempotent.enums;

import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 描述 ：幂等 MQ 消费状态枚举
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum IdempotentMQConsumeStatusEnum implements IEnum<String> {

    /**
     * 消费中
     */
    CONSUMING("0"),

    /**
     * 已消费
     */
    CONSUMED("1");

    private final String value;

    /**
     * 如果消费状态等于消费中，返回失败
     *
     * @param consumeStatus 消费状态
     * @return 是否消费失败
     */
    public static boolean isError(String consumeStatus) {
        return Objects.equals(CONSUMING.value, consumeStatus);
    }
}
