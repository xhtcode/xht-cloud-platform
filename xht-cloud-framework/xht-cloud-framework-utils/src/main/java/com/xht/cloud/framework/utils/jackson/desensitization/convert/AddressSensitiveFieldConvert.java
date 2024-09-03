package com.xht.cloud.framework.utils.jackson.desensitization.convert;

import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.utils.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：地址脱敏策略，保留省市信息，其余部分脱敏为**
 *
 * @author 小糊涂
 **/
public class AddressSensitiveFieldConvert implements ISensitiveFieldConvert {

    /**
     * 地址脱敏策略，保留省市信息，其余部分脱敏为**
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        if (!StringUtils.hasText(source)) return source;
        int length = source.length();
        int indes = source.indexOf("区");
        if (indes == -1) {
            indes = source.indexOf("市");
        }
        source = source.substring(0, indes + 1);
        return source + "*".repeat(Math.max(0, length - indes));
    }

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.ADDRESS;
    }
}
