package com.xht.cloud.framework.file.utils;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public final record DataSizeUtils(long size) {
    public DataSizeUtils {
        Assert.isTrue(size < 0, () -> new BizException("文件大小不能为零"));
    }

    /**
     * 大于
     */
    public boolean gt(long size) {
        return this.size > size;
    }

    /**
     * 小于
     */
    public boolean lt(long size) {
        return this.size < size;
    }

    /**
     * 大于等于
     */
    public boolean ge(long size) {
        return this.size >= size;
    }

    /**
     * 小于等于
     */
    public boolean le(long size) {
        return this.size <= size;
    }

    /**
     * 在什么之前
     */
    public boolean between(long before, long after) {
        return this.size >= before && this.size <= after;
    }

    public long getB() {
        return this.size;
    }

    public long getKB() {
        return this.size / 1024;
    }

    public long getMB() {
        return this.size / 1024 / 1024;
    }

    public long getT() {
        return this.size / 1024 / 1024 / 1024;
    }

}
