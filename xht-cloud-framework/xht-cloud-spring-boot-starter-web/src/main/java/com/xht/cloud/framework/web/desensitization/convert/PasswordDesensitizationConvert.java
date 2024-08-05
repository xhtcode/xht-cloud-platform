package com.xht.cloud.framework.web.desensitization.convert;

/**
 * 描述 ：密码脱敏策略，统一显示为******
 *
 * @author 小糊涂
 **/
public class PasswordDesensitizationConvert implements DesensitizationConvert {

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
}
