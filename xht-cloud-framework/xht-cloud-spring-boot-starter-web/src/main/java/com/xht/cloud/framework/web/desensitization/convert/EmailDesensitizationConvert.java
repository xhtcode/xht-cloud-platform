package com.xht.cloud.framework.web.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;

/**
 * 描述 ：邮箱脱敏策略，保留邮箱用户名第一个字符和@符号前后部分
 *
 * @author 小糊涂
 **/
public class EmailDesensitizationConvert implements DesensitizationConvert {

    /**
     * 邮箱脱敏策略，保留邮箱用户名第一个字符和@符号前后部分
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        return DesensitizedUtil.email(source);
    }
}
