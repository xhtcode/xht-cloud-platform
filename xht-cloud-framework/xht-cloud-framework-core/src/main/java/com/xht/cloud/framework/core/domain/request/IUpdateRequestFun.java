package com.xht.cloud.framework.core.domain.request;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 描述 ：request 可以获取主键
 *
 * @author 小糊涂
 **/
@FunctionalInterface
public interface IUpdateRequestFun<T extends Serializable> {

    /**
     * 获取主键
     */
    @JsonIgnore
    T getPkId();

}
