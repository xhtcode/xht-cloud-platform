package com.xht.cloud.framework.redis.key;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.framework.redis.constant.RedisConstant;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.springframework.lang.NonNull;

/**
 * 描述 ：redis key 生成
 *
 * @author 小糊涂
 **/
public final class RedisKeyTool {

    /**
     * 生成key
     */
    public static String createKey(String... key) {
        return ArrayUtil.join(key, RedisConstant.KEY_DELIMITED);
    }

    /**
     * 格式化 Key
     *
     * @param keys 格式化的参数
     * @return Key
     */
    public static String createKeyTemplate(@NonNull final String keyTemplate, @NonNull String... keys) {
        if (!StringUtils.hasText(keyTemplate)) return keyTemplate;
        if (ArrayUtil.isEmpty(keys)) return keyTemplate;
        Object[] objects = new Object[keys.length];
        System.arraycopy(keys, 0, objects, 0, objects.length);
        return StrFormatter.format(keyTemplate, objects);
    }

    /**
     * 生成key
     */
    public static RedisKeyBO createKeyBo(String... key) {
        return RedisKeyBO.builder().keyName(createKey(key)).build();
    }

    /**
     * 生成key
     */
    public static RedisKeyBO createKeyBoTemplate(@NonNull final String keyTemplate, @NonNull String... keys) {
        return RedisKeyBO.builder().keyName(createKeyTemplate(keyTemplate, keys)).build();
    }

}

