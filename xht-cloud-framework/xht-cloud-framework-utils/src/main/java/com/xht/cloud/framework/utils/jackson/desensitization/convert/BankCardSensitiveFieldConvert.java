package com.xht.cloud.framework.utils.jackson.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.jackson.desensitization.constant.SensitiveFieldConstant;
import com.xht.cloud.framework.utils.support.StringUtils;

/**
 * 描述 ：银行卡号脱敏策略，保留前四位和后三位
 *
 * @author 小糊涂
 **/
public class BankCardSensitiveFieldConvert implements ISensitiveFieldConvert {

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


    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.BANK_CARD;
    }
}
