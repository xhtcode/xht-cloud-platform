package com.xht.cloud.framework.jackson.desensitization.convert;

import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：密码脱敏策略，统一显示为******
 *
 * @author 小糊涂
 **/
public class PasswordSensitiveFieldConvert implements ISensitiveFieldConvert {

    private final static String PASSWORD_HIDE = "*********";

    /**
     * 密码脱敏策略
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        return PASSWORD_HIDE;
    }

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.PASSWORD;
    }
}
