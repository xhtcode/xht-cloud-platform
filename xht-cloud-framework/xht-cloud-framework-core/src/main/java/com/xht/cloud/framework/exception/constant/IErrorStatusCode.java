package com.xht.cloud.framework.exception.constant;


import java.io.Serializable;

/**
 * 描述 ： 异常状态码
 *
 * @author 小糊涂
 * @see UserErrorStatusCode 用户相关 10000到 19999 段
 **/
public interface IErrorStatusCode extends Serializable {

    /**
     * 默认异常状态
     */
    Integer DEFAULT_ERROR_CODE = 500;

    /**
     * @return 状态
     */
    Integer getCode();

    /**
     * @return 描述
     */
    String getMessage();

}
