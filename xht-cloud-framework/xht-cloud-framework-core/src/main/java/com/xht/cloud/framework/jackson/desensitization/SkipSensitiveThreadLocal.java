package com.xht.cloud.framework.jackson.desensitization;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ： 脱敏字段
 *
 * @author : 小糊涂
 **/
@Slf4j
public final class SkipSensitiveThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    private static final String DELIMITER = StringConstant.DEFAULT_DELIMITER;

    /**
     * 判断是否跳过当前字段
     */
    public static boolean isSkip(String field) {
        return Objects.equals(SensitiveFieldConstant.SKIP_ALL_FIELDS, THREAD_LOCAL.get()) || StrUtil.contains(THREAD_LOCAL.get(), field);
    }

    /**
     * 删除 线程中的信息
     */
    public static void remove() {
        try {
            THREAD_LOCAL.remove();
        } catch (Exception e) {
            log.error("关闭出现了错误 {}", e.getMessage(), e);
        }
    }

    /**
     * 全部跳过字段
     */
    public static void skipAll() {
        skip(SensitiveFieldConstant.SKIP_ALL_FIELDS);
    }

    /***
     * 自定义跳过的字段
     *
     * @param fieldNames 字段
     */
    public static void skip(String... fieldNames) {
        String join = ArrayUtil.join(fieldNames, DELIMITER);
        THREAD_LOCAL.set(join);
    }
}
