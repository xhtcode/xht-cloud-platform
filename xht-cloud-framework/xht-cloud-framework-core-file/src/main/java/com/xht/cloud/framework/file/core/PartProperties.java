package com.xht.cloud.framework.file.core;

import com.xht.cloud.framework.core.config.CommonConfigProperties;
import lombok.Data;

/**
 * 描述 ： 分块配置
 *
 * @author 小糊涂
 **/
@Data
public class PartProperties extends CommonConfigProperties {

    /**
     * 分块大小，配置单位为byte，默认为5242880(5MB)
     */
    private long size = 5242880;

    /**
     * 分块上传时建议并发数，默认为3
     */
    private int thread = 3;
}
