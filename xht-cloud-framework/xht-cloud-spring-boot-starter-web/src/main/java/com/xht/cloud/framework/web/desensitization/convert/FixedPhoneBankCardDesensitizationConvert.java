package com.xht.cloud.framework.web.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;

/**
 * 描述 ：固定电话脱敏
 *
 * @author 小糊涂
 **/
public class FixedPhoneBankCardDesensitizationConvert implements DesensitizationConvert {

    /**
     * 固定电话脱敏
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        return DesensitizedUtil.fixedPhone(source);
    }
}
