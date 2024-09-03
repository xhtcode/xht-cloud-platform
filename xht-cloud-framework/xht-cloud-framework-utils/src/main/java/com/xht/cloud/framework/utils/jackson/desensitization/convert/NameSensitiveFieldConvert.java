package com.xht.cloud.framework.utils.jackson.desensitization.convert;

import com.xht.cloud.framework.utils.chinese.ChineseNameUtils;
import com.xht.cloud.framework.utils.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：姓名脱敏策略，保留姓氏第一个字符，其余部分脱敏为**
 *
 * @author 小糊涂
 **/
public class NameSensitiveFieldConvert implements ISensitiveFieldConvert {

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

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.NAME;
    }
}
