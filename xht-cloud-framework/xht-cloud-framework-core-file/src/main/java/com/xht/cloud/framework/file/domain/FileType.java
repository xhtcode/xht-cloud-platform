package com.xht.cloud.framework.file.domain;

import cn.hutool.core.util.IdUtil;

/**
 * 描述 ：文件类型
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public record FileType(String type, String contentType, String desc) {

    /**
     * @return 生成的文件名称
     */
    public String generateName() {
        return IdUtil.objectId();
    }

    /**
     * @param name 文件名称前缀
     * @return 生成的文件名称
     */
    public String generateName(String name) {
        return String.format("%s.%s", name, this.type);
    }

    /**
     * @return 生成的文件后缀
     */
    public String getFileSuffix() {
        return this.type.toLowerCase();
    }

}
