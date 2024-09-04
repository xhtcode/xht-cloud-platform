package com.xht.cloud.framework.domain.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;

import java.io.Serializable;

/**
 * 描述 ：request 可以获取主键
 *
 * @author 小糊涂
 **/
public interface IUpdateRequestFun<T extends Serializable> {

    /**
     * 获取主键
     */
    @JsonIgnore
    default T getPkId() {
       throw new BizException(GlobalErrorStatusCode.ERROR_CONFIGURATION);
    }
}
