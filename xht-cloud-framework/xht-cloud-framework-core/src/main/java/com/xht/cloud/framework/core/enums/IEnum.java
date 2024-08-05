package com.xht.cloud.framework.core.enums;

import java.io.Serializable;

/**
 * 描述 ： 枚举公共接口
 *
 * @author 小糊涂
 **/
public interface IEnum<T extends Serializable> {

    /**
     * @return 字典值
     */
    T getValue();
}
