package com.xht.cloud.framework.redis.idempotent.enums;

import com.xht.cloud.framework.core.enums.IEnum;

/**
 * 描述 ：幂等类型
 *
 * @author 小糊涂
 **/
public enum IdempotentTypeEnum implements IEnum<String> {

    /**
     * 基于 Token 方式验证
     */
    TOKEN,

    /**
     * 基于方法参数方式验证
     */
    PARAM,

    /**
     * 基于 SpEL 表达式方式验证
     */
    SPEL;

    /**
     * @return 字典值
     */
    @Override
    public String getValue() {
        return this.name();
    }
}
