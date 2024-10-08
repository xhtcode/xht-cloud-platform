package com.xht.cloud.framework.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ：json工具类
 *
 * @author 小糊涂
 **/
@Slf4j
public final class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModules(new CustomJacksonModule());
    }

    /**
     * Object 转化成 json字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new BizException("json 序列化错误了", e);
        }
    }

    /**
     * 将json形式的字符串数据转换成单个对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<?> clazz) {
        if (StringUtils.hasText(json) && Objects.nonNull(clazz)) {
            try {
                if (Objects.equals(String.class, clazz)) {
                    return (T) json;
                }
                return (T) objectMapper.readValue(json, clazz);
            } catch (Exception e) {
                throw new BizException("json 序列化有问题!", e);
            }
        }
        return null;
    }

    /**
     * 将json形式的字符串数据转换成多个对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.hasText(json) && Objects.nonNull(typeReference)) {
            try {
                if (typeReference.getType().equals(String.class)) {
                    return (T) json;
                }
                return (T) objectMapper.readValue(json, typeReference);
            } catch (Exception e) {
                throw new BizException("json 序列化有问题!", e);
            }
        }

        return null;
    }
}
