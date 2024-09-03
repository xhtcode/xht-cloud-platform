package com.xht.cloud.framework.utils.jackson.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：邮箱脱敏策略，保留邮箱用户名第一个字符和@符号前后部分
 *
 * @author 小糊涂
 **/
public class EmailSensitiveFieldConvert implements ISensitiveFieldConvert {

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


    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.EMAIL;
    }
}
