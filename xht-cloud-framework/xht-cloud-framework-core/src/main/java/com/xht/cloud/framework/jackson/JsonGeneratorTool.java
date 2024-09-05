package com.xht.cloud.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;

import java.util.Objects;

/**
 * 描述 ：{@link JsonGenerator} 扩展
 *
 * @author : 小糊涂
 **/
public final class JsonGeneratorTool {

    /**
     * 过去字段名称
     *
     * @param jsonGenerator {@link JsonGenerator} 用于输出结果Json内容的生成器
     * @return 字段名称
     */
    public static String getFieldName(JsonGenerator jsonGenerator) {
        if (Objects.isNull(jsonGenerator)) return null;
        String currentName = null;
        JsonStreamContext jsc = jsonGenerator.getOutputContext();
        if (null != jsc) {
            currentName = jsc.getCurrentName();
        }
        return currentName;
    }
}
