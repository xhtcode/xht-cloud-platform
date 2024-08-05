package com.xht.cloud.framework.web.desensitization.convert;

import com.xht.cloud.framework.utils.chinese.ChineseNameUtils;

/**
 * 描述 ：姓名脱敏策略，保留姓氏第一个字符，其余部分脱敏为**
 *
 * @author 小糊涂
 **/
public class NameDesensitizationConvert implements DesensitizationConvert {

    /**
     * 姓名脱敏策略，保留姓氏第一个字符，其余部分脱敏为**
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        return ChineseNameUtils.desensitization(source);
    }
}
