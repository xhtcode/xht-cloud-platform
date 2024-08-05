package com.xht.cloud.framework.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述 ：公共的配置类
 *
 * @author 小糊涂
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonConfigProperties implements Serializable {

    /**
     * 是否关闭配置模式  关闭 false 打开  true 默认 true
     */
    private boolean enabled;
}
