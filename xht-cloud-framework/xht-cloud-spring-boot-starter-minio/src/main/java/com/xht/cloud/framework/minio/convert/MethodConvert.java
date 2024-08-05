package com.xht.cloud.framework.minio.convert;

import com.xht.cloud.framework.file.constant.OssRequestMethod;
import io.minio.http.Method;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 描述 ： 请求方式
 *
 * @author 小糊涂
 **/
public class MethodConvert {
    private static final Method[] values = Method.values();

    public static Method convert(@NotNull OssRequestMethod source) {
        for (Method value : values) {
            if (Objects.equals(value.name(), source.name())) {
                return value;
            }
        }
        return null;
    }
}
