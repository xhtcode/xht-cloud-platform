package com.xht.cloud.framework.jackson.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：手机号脱敏策略，保留前三位和后四位
 *
 * @author 小糊涂
 **/
public class PhoneSensitiveFieldConvert implements ISensitiveFieldConvert {

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

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.PHONE;
    }
}
