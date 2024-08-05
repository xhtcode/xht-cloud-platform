package com.xht.cloud.framework.web.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.support.StringUtils;

/**
 * 描述 ：银行卡号脱敏策略，保留前四位和后三位
 *
 * @author 小糊涂
 **/
public class BankCardDesensitizationConvert implements DesensitizationConvert {

    /**
     * 银行卡号脱敏策略，保留前四位和后三位
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        if (!StringUtils.hasText(source)) return source;
        return DesensitizedUtil.bankCard(source);
    }
}
