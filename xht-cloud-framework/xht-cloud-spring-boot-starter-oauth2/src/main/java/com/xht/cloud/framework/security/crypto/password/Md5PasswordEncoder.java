package com.xht.cloud.framework.security.crypto.password;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.utils.secret.MD5Utils;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 描述 ：密码编码器
 *
 * @author 小糊涂
 **/
public class Md5PasswordEncoder implements PasswordEncoder {
    /**
     * 密码加密
     *
     * @param rawPassword 铭文密码
     * @return 加密后的密码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String password = StringUtils.str(rawPassword);
        Assert.hasText(password, "明文密码不能为空");
        return MD5Utils.generateSignature(password);
    }

    /**
     * @param rawPassword     明文密码
     * @param encodedPassword 加密密码
     * @return true 成功 false失败
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
