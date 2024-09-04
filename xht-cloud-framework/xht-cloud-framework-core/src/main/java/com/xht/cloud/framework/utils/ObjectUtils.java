package com.xht.cloud.framework.utils;

/**
 * 描述 ：{@link ObjectUtils}工具类扩展
 *
 * @author 小糊涂
 **/
public class ObjectUtils extends org.springframework.util.ObjectUtils {

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
