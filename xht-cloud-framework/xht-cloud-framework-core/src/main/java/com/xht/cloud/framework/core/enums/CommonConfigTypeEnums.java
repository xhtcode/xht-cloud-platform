package com.xht.cloud.framework.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * 描述 ：公共的配置类
 *
 * @author 小糊涂
 **/
@AllArgsConstructor
public enum CommonConfigTypeEnums implements IEnum<String> {
    /**
     * properties
     */
    PROPERTIES,
    /**
     * 内存
     */
    MEMORY,
    /**
     * 数据库
     */
    JDBC,
    /**
     * REDIS
     */
    REDIS,
    /**
     * HTTP
     */
    HTTP;

    /**
     * @return 字典值
     */
    @JsonValue
    @Override
    public String getValue() {
        return this.name();
    }
}
