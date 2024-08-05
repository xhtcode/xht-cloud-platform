package com.xht.cloud.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import com.xht.cloud.framework.redis.service.RedisService;
import com.xht.cloud.framework.security.exception.CaptchaException;
import com.xht.cloud.framework.security.resource.captcha.CaptchaResult;
import com.xht.cloud.framework.security.resource.captcha.ICaptchaService;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ：验证码
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@RefreshScope
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    private final RedisService redisService;

    @Override
    public CaptchaResult createCaptcha() throws CaptchaException {
        String uuid = IdUtil.simpleUUID();
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        ShearCaptcha lineCaptcha = CaptchaUtil.createShearCaptcha(100, 30);
        lineCaptcha.setGenerator(randomGenerator);
        String imageBase64 = lineCaptcha.getImageBase64();
        CaptchaResult captchaVO = new CaptchaResult();
        captchaVO.setUuid(uuid);
        captchaVO.setTimeout(5 * 59 * 1000);
        captchaVO.setImg(String.format("data:image/png;base64,%s",imageBase64));
        redisService.set(getCodeRedisKey(uuid), lineCaptcha.getCode(), 5, TimeUnit.MINUTES);
        return captchaVO;
    }

    @Override
    public void checkCaptcha(String uuid, String captcha) throws CaptchaException {
        String codeRedisKey = getCodeRedisKey(uuid);
        if (redisService.getExpire(codeRedisKey) < 0) {
            throw new CaptchaException("验证码错误！");
        }
        String o = (String) redisService.get(codeRedisKey);
        if (!Objects.equals(captcha, o)) {
            throw new CaptchaException("验证码错误！");
        }
    }


    @Override
    public void clearCaptcha(String uuid) {
        if (StringUtils.hasText(uuid)) {
            redisService.delete(uuid);
        }
    }

}
