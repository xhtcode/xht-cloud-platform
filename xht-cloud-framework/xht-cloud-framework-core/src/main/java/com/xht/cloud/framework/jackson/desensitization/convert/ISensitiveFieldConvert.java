package com.xht.cloud.framework.jackson.desensitization.convert;

import com.xht.cloud.framework.core.strategy.IStrategy;

import java.io.Serializable;

/**
 * 描述 ：自定义脱敏策略 基类
 *
 * @author 小糊涂
 **/
public interface ISensitiveFieldConvert extends IStrategy<String>, Serializable {

    /**
     * 数据脱敏具体实现
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    String desensitization(String source);

}
