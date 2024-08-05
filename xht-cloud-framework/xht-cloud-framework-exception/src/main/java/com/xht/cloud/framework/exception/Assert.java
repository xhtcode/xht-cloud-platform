package com.xht.cloud.framework.exception;


import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 描述 ：自定义异常断言
 *
 * @author 小糊涂
 **/
public final class Assert {


    // -----------------------------------------------字符串相关 start-----------------------------------------------

    /**
     * 字符串为空抛出异常
     *
     * @param str        字符串
     * @param errMessage 异常描述
     */
    public static void hasText(String str, String errMessage) {
        if (str == null || str.isBlank()) throw new RuntimeException(errMessage);
    }

    /**
     * 字符串为空抛出异常
     *
     * @param str           字符串
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void hasText(String str, Supplier<X> errorSupplier) throws X {
        if (str == null || str.isBlank()) throw errorSupplier.get();
    }

    // -----------------------------------------------字符串相关 end-----------------------------------------------
    // -----------------------------------------------对象 start-----------------------------------------------

    /**
     * 对象为空抛出异常
     *
     * @param object     对象
     * @param errMessage 异常描述
     */
    public static void notNull(Object object, String errMessage) {
        if (Objects.isNull(object)) throw new RuntimeException(errMessage);
    }

    /**
     * 对象为空抛出异常
     *
     * @param object        对象
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notNull(Object object, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(object)) throw errorSupplier.get();
    }
    // -----------------------------------------------对象 end-----------------------------------------------
    // -----------------------------------------------集合 start-----------------------------------------------

    /**
     * 集合为空抛出异常
     *
     * @param collection 集合
     * @param errMessage 异常信息
     */
    public static void notEmpty(Collection<?> collection, String errMessage) {
        if (Objects.isNull(collection) || collection.isEmpty()) throw new RuntimeException(errMessage);
    }

    /**
     * 集合为空抛出异常
     *
     * @param collection    集合
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notEmpty(Collection<?> collection, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(collection) || collection.isEmpty()) throw errorSupplier.get();
    }


    /**
     * map集合为空抛出异常
     *
     * @param map        map集合
     * @param errMessage 异常信息
     */
    public static void notEmpty(Map<?, ?> map, String errMessage) {
        if (Objects.isNull(map) || map.isEmpty()) throw new RuntimeException(errMessage);
    }


    /**
     * map集合为空抛出异常
     *
     * @param map           map集合
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notEmpty(Map<?, ?> map, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(map) || map.isEmpty()) throw errorSupplier.get();
    }

    // -----------------------------------------------集合 end-----------------------------------------------
    // -----------------------------------------------数组 start-----------------------------------------------

    /**
     * 数组为空抛出异常
     *
     * @param array      数组
     * @param errMessage 异常信息
     */
    public static <T> void notEmpty(T[] array, String errMessage) {
        if (array == null || array.length == 0) throw new RuntimeException(errMessage);
    }


    /**
     * 数组为空抛出异常
     *
     * @param array         数组
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <T, X extends Throwable> void notEmpty(T[] array, Supplier<X> errorSupplier) throws X {
        if (array == null || array.length == 0) throw errorSupplier.get();
    }

    // -----------------------------------------------数组 end-----------------------------------------------
    // -----------------------------------------------其他 start-----------------------------------------------

    /**
     * 断言是否为假，如果为 true 抛出{@link BizException}
     *
     * @param flag    布尔值
     * @param message 错误信息
     */
    public static void isTrue(boolean flag, String message) {
        isTrue(flag, () -> new BizException(message));
    }


    /**
     * 断言是否为假，如果为 {@code true} 抛出指定类型异常<br>
     * 并使用指定的函数获取错误信息返回
     * <code >
     * Assert.isTrue(i > 0, ()->;{
     * // to query relation message
     * return new IllegalArgumentException("relation message to return");
     * });
     * </code>
     *
     * @param <X>           异常类型
     * @param expression    布尔值
     * @param errorSupplier 指定断言不通过时抛出的异常
     * @throws X if expression is {@code false}
     */
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<X> errorSupplier) throws X {
        if (expression) {
            throw errorSupplier.get();
        }
    }

    // -----------------------------------------------其他 end-----------------------------------------------

}
