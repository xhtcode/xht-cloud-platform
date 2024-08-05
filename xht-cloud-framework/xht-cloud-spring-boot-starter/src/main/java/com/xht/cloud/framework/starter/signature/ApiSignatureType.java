package com.xht.cloud.framework.starter.signature;

import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum ApiSignatureType implements IEnum<String> {

    PROPERTIES,

    DATABASE,

    RPC;

    /**
     * @return 字典值
     */
    @Override
    public String getValue() {
        return this.name();
    }
}
