package com.xht.cloud.framework.core.builder;

import java.io.Serializable;

/**
 * 描述 ：建造者模式接口定义
 *
 * @param <T> 建造对象类型
 * @author 小糊涂
 **/
public interface IBuilder<T> extends Serializable {

    /**
     * 构建方法
     *
     * @return 被构建的对象
     */
    T build();
}
