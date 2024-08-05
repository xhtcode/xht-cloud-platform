package com.xht.cloud.framework.web.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;

/**
 * 描述 ：手机号脱敏策略，保留前三位和后四位
 *
 * @author 小糊涂
 **/
public class PhoneDesensitizationConvert implements DesensitizationConvert {

    /**
     * 手机号脱敏策略，保留前三位和后四位
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        return DesensitizedUtil.mobilePhone(source);
    }
}
