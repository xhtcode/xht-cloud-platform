package com.xht.cloud.framework.security.resource.captcha;


import com.xht.cloud.framework.security.exception.CaptchaException;
import com.xht.cloud.framework.utils.StringUtils;

/**
 * 描述 ：验证码
 *
 * @author 小糊涂
 * @version : 1.0
 **/
public interface ICaptchaService {

    /**
     * 获取redis 的key
     */
    default String getCodeRedisKey(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("验证码生成失败！");
        }
        return String.format("oauth2:code:%s", uuid);
    }

    /**
     * 生成验证码
     */
    CaptchaResult createCaptcha() throws CaptchaException;

    /**
     * 校验验证码是否正确
     */
    void checkCaptcha(String uuid, String captcha) throws CaptchaException;

    /**
     * 清理验证码
     */
    void clearCaptcha(String uuid);
}
