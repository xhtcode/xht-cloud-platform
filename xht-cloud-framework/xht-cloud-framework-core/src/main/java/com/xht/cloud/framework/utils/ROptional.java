package com.xht.cloud.framework.utils;


import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述 ：简化{@link R} 的访问操作
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public final class ROptional<T> implements Serializable {
    private final R<T> original;

    private ROptional(R<T> original) {
        this.original = original;
    }

    /**
     * 初始化
     *
     * @param value 原始 不能为空
     * @param <T>   {@link Serializable}
     * @return this
     */
    public static <T> ROptional<T> of(R<T> value) {
        Assert.notNull(value, "original is value empty!");
        return new ROptional<>(value);
    }

    /**
     * 初始化
     *
     * @param value 原始 能为空
     * @param <T>   {@link Serializable}
     * @return this
     */
    public static <T> ROptional<T> ofNullable(R<T> value) {
        return new ROptional<>(value);
    }


    /**
     * 读取{@code code}的值
     *
     * @return 返回code的值
     */
    public Integer getCode() {
        if (Objects.isNull(original)) {
            return null;
        }
        return original.getCode();
    }


    /**
     * 读取{@code data}的值
     *
     * @return 返回 Optional 包装的data
     */
    public Optional<T> getData() {
        if (Objects.isNull(original)) {
            return Optional.empty();
        }
        return Optional.ofNullable(original.getData());
    }

    /**
     * 读取{@code data}的值
     *
     * @return 返回 Optional 包装的data
     */
    public Optional<T> getSuccessData() {
        if (isSuccess()) {
            return Optional.ofNullable(original.getData());
        }
        throw new BizException("失败状态，无法获取数据!");
    }


    /**
     * 是否成功
     *
     * @return 返回ture表示成功
     */
    public boolean isSuccess() {
        if (Objects.isNull(original)) {
            return false;
        }
        return original.getSuccess();
    }

}
