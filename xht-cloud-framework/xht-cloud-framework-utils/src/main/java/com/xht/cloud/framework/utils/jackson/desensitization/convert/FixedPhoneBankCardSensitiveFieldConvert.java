package com.xht.cloud.framework.utils.jackson.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：固定电话脱敏
 *
 * @author 小糊涂
 **/
public class FixedPhoneBankCardSensitiveFieldConvert implements ISensitiveFieldConvert {

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

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.FIXED_PHONE;
    }
}
