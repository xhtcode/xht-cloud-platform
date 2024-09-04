package com.xht.cloud.framework.utils;


import com.xht.cloud.framework.constant.StringConstant;

/**
 * 描述 ：{@link StringUtils}工具类扩展
 *
 * @author 小糊涂
 **/
public final class StringUtils extends org.springframework.util.StringUtils {

    private static final String regex = "[\\u4e00-\\u9fff]+"; // 匹配一个或多个汉字


    public static boolean isEmpty(String str) {
        return !hasText(str);
    }

    /**
     * 转换成字符串
     */
    public static String str(Object obj) {
        return str(obj, null);
    }

    /**
     * 转换成字符串
     */
    public static String str(Object obj, String defaultValue) {
        return (obj == null) ? defaultValue : obj.toString();
    }


    /**
     * 替换中文
     *
     * @param str        源字符串
     * @param replaceStr 替换的字符变成的什么
     * @return 替换后的字符串
     */
    public static String replaceChinese(String str, String replaceStr) {
        if (hasText(str)) {
            return str.replaceAll(regex, replaceStr);
        }
        return str;
    }


    /**
     * 当字符串空的时候，返回默认值,不为空返回当前值
     */
    public static String emptyToDefaultNull(String value) {
        return emptyToDefault(value, StringConstant.EMPTY_STR);
    }

    /**
     * 当字符串空的时候，返回默认值
     */
    public static String emptyToDefault(String value, String defaultValue) {
        if (hasText(value)) {
            return value;
        }
        return defaultValue;
    }

    /**
     * 比较两个字符串（大小写敏感）。
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 比较两个字符串是否相等，规则如下
     * <ul>
     *     <li>str1和str2都为{@code null}</li>
     *     <li>忽略大小写使用{@link String#equalsIgnoreCase(String)}判断相等</li>
     *     <li>不忽略大小写使用{@link String#contentEquals(CharSequence)}判断相等</li>
     * </ul>
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     * @since 3.2.0
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }

}
