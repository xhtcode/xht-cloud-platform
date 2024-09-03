package com.xht.cloud.framework.core.strategy;

/**
 * 描述 ：策略
 *
 * @author : 小糊涂
 **/
@FunctionalInterface
public interface IStrategy<T> {

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    T support();

}
