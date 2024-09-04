package com.xht.cloud.framework.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：公共实体返回
 *
 * @author 小糊涂
 **/
@Schema(description = "全局响应实体")
public abstract class AbstractResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
