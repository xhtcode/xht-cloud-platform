package com.xht.cloud.framework.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 描述 ：id 标识
 *
 * @author 小糊涂
 **/
@Getter
@Setter
public abstract class Identifier<ID> implements Serializable {
    /**
     * ID.
     */
    protected ID id;
}
